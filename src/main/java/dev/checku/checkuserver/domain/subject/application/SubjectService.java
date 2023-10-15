package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.bookmark.application.BookmarkService;
import dev.checku.checkuserver.domain.bookmark.entity.Bookmark;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.notification.exception.SubjectHasVacancyException;
import dev.checku.checkuserver.domain.notification.exception.SubjectNotFoundException;
import dev.checku.checkuserver.domain.portal.application.PortalFeignClient;
import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.domain.portal.dto.PortalResponse;
import dev.checku.checkuserver.domain.subject.dto.GetAllSubjectsRequest;
import dev.checku.checkuserver.domain.subject.dto.GetAllSubjectsResponse;
import dev.checku.checkuserver.domain.subject.dto.SearchSubjectRequest;
import dev.checku.checkuserver.domain.subject.dto.SearchSubjectResponse;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.subject.enums.Department;
import dev.checku.checkuserver.domain.subject.enums.Grade;
import dev.checku.checkuserver.domain.subject.enums.Type;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.subject.repository.SubjectRepository;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.global.util.PortalRequestFactory;
import dev.checku.checkuserver.global.util.SubjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    @Value("${thread.poolSize:3}")
    private int poolSize;

    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final SubjectRepository subjectRepository;
    private final PortalRequestFactory portalRequestFactory;

    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public List<GetAllSubjectsResponse> getSubjectsByDepartment(GetAllSubjectsRequest request) {
        User user = userService.getBy(request.getUserId());
        List<SubjectNumber> subjectList = getMySubjectsFromMySubject(user);

        Department department = Department.valueOf(request.getDepartment());
        Grade grade = Grade.setNumber(request.getGrade());
        Type type = Type.setType(request.getType());
        Boolean inVacancy = request.getVacancy();

        PortalResponse response = getAllSubjectsFromPortalByDepartmentAndType(department, type); // 전필, 전선은 이미 필터링

        // OTHER은 따로 분류하지 않음 -> 따라서 애플리케이션에서 따로 구분해야함
        return response.getSubjectDetails()
                .stream()
                .filter(subject -> grade.matchGrade(subject.getGrade()))
                .filter(subject -> type.matchType(subject.getSubjectType()))
                .filter(subject -> isVacancy(inVacancy, subject)) // vacancy 필터링
                .map(subject -> GetAllSubjectsResponse.from(subject, subjectList)).collect(Collectors.toList());
    }

    private List<SubjectNumber> getMySubjectsFromMySubject(User user) {
        return bookmarkService.getAllSubjectsByUser(user)
                .stream().map(Bookmark::getSubjectNumber)
                .collect(Collectors.toList());
    }


    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public void validateSubjectHasVacancy(SubjectNumber subjectNumber) {
        PortalResponse response = getAllSubjectsFromPortalBySubjectNumber(subjectNumber);
        try {
            PortalResponse.SubjectDetail subjectDetail = response.getSubjectDetails().get(0);
            // 빈 자리 존재하는 과목인 경우 알림 제공 안됨
            if (SubjectUtils.hasVacancy(subjectDetail.getNumberOfPeople())) {
                throw new SubjectHasVacancyException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SubjectNotFoundException();
        }
    }


    @Transactional
    public void insertSubjects() {
        PortalResponse subjectListDto = getAllSubjectsFromPortal();
        List<PortalResponse.SubjectDetail> subjects = subjectListDto.getSubjectDetails();
        List<Subject> subjectList = new ArrayList<>();

        for (PortalResponse.SubjectDetail subjectDetail : subjects) {
            if (subjectDetail.getSubjectNumber() != null) {
                Subject subject = Subject.classifyMajorOrLiberalArts(new SubjectNumber(subjectDetail.getSubjectNumber()), subjectDetail.getSubjectName(), subjectDetail.getSubjectType());
                subjectList.add(subject);
            }
        }
        subjectRepository.saveAll(subjectList);
    }

    public Subject getBy(SubjectNumber subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public Slice<SearchSubjectResponse> getSubjectsByKeyword(SearchSubjectRequest request, Pageable pageable) {
        User user = userService.getBy(request.getUserId());

        List<SubjectNumber> subjectNumbers = getMySubjectsFromMySubject(user);
        List<Subject> subject = subjectRepository.findByKeyword(request.getSearchQuery(), pageable);

        ForkJoinPool pool = new ForkJoinPool(poolSize);
        List<SearchSubjectResponse> results = new ArrayList<>();

        try {
            pool.submit(() -> {
                List<SearchSubjectResponse> responses = subject.parallelStream()
                        .map(mySubject -> {
                            PortalResponse response = getAllSubjectsFromPortalBySubjectNumber(mySubject.getSubjectNumber());
                            if (response.getSubjectDetails().isEmpty()) return null;
                            else return SearchSubjectResponse.from(response.getSubjectDetails().get(0), subjectNumbers);
                        })
                        .collect(Collectors.toList());
                results.addAll(responses);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }

        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private PortalResponse getAllSubjectsFromPortal() {
        return portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", "")
        );
    }

    private PortalResponse getAllSubjectsFromPortalBySubjectNumber(SubjectNumber subjectNumber) {
        PortalResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", subjectNumber.getValue())
        );

        if (response.getSubjectDetails().isEmpty()) {
            updatePortalSessionAndRetry();
        }


        return response;
    }

    public PortalResponse getAllSubjectsFromPortalByDepartmentAndType(Department department, Type type) {

        PortalResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody(type.getValue(), department.getValue(), "")
        );

        if (response.getSubjectDetails() == null) {
            updatePortalSessionAndRetry();
        }

        return response;
    }


    private void updatePortalSessionAndRetry() {
        portalSessionService.updatePortalSession();

        throw new SubjectRetryException();
    }

    private static boolean isVacancy(boolean isVacancy, PortalResponse.SubjectDetail subject) {
        return !isVacancy || SubjectUtils.hasVacancy(subject.getNumberOfPeople());
    }
}
