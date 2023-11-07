package dev.checku.checkuserver.domain.subject.application.service;

import dev.checku.checkuserver.domain.bookmark.adapter.out.persistence.BookmarkJpaEntity;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.domain.notification.exception.SubjectHasVacancyException;
import dev.checku.checkuserver.domain.notification.exception.SubjectNotFoundException;
import dev.checku.checkuserver.domain.session.application.service.PortalSessionService;
import dev.checku.checkuserver.domain.temp.PortalSubjectResponse;
import dev.checku.checkuserver.domain.temp.PortalFeignClient;
import dev.checku.checkuserver.domain.subject.adpater.in.web.GetAllSubjectsRequest;
import dev.checku.checkuserver.domain.subject.adpater.in.web.GetAllSubjectsResponse;
import dev.checku.checkuserver.domain.subject.adpater.in.web.SearchSubjectRequest;
import dev.checku.checkuserver.domain.subject.adpater.in.web.SearchSubjectResponse;
import dev.checku.checkuserver.domain.subject.adpater.out.persistence.SubjectJpaEntity;
import dev.checku.checkuserver.domain.subject.domain.Department;
import dev.checku.checkuserver.domain.subject.domain.Type;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.subject.adpater.out.persistence.SubjectSpringDataRepository;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.global.util.PortalRequestFactory;
import dev.checku.checkuserver.global.util.SubjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    @Value("${thread.poolSize:3}")
    private int poolSize;

//    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final SubjectSpringDataRepository subjectRepository;
    private final PortalRequestFactory portalRequestFactory;

    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public List<GetAllSubjectsResponse> getSubjectsByDepartment(GetAllSubjectsRequest request) {
//        UserJpaEntity userJpaEntity = userService.getBy(request.getUserId());
//        List<SubjectNumber> subjectList = getMySubjectsFromMySubject(userJpaEntity);
//
//        Department department = Department.valueOf(request.getDepartment());
//        Grade grade = Grade.setNumber(request.getGrade());
//        Type type = Type.setType(request.getType());
//        Boolean inVacancy = request.getVacancy();
//
//        PortalResponse response = getAllSubjectsFromPortalByDepartmentAndType(department, type); // 전필, 전선은 이미 필터링
//
//        // OTHER은 따로 분류하지 않음 -> 따라서 애플리케이션에서 따로 구분해야함
//        return response.getSubjectDetails()
//                .stream()
//                .filter(subject -> grade.matchGrade(subject.getGrade()))
//                .filter(subject -> type.matchType(subject.getSubjectType()))
//                .filter(subject -> isVacancy(inVacancy, subject)) // vacancy 필터링
//                .map(subject -> GetAllSubjectsResponse.from(subject, subjectList)).collect(Collectors.toList());
        return null;
    }

    private List<SubjectNumber> getMySubjectsFromMySubject(UserJpaEntity userJpaEntity) {
        return bookmarkService.getAllSubjectsByUser(userJpaEntity)
                .stream().map(BookmarkJpaEntity::getSubjectNumber)
                .collect(Collectors.toList());
    }


    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public void validateSubjectHasVacancy(SubjectNumber subjectNumber) {
        PortalSubjectResponse response = getAllSubjectsFromPortalBySubjectNumber(subjectNumber);
        try {
            PortalSubjectResponse.SubjectDetail subjectDetail = response.getSubjectDetails().get(0);
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
        PortalSubjectResponse subjectListDto = getAllSubjectsFromPortal();
        List<PortalSubjectResponse.SubjectDetail> subjects = subjectListDto.getSubjectDetails();
        List<SubjectJpaEntity> subjectJpaEntityList = new ArrayList<>();

        for (PortalSubjectResponse.SubjectDetail subjectDetail : subjects) {
            if (subjectDetail.getSubjectNumber() != null) {
                SubjectJpaEntity subjectJpaEntity = SubjectJpaEntity.classifyMajorOrLiberalArts(new SubjectNumber(subjectDetail.getSubjectNumber()), subjectDetail.getSubjectName(), subjectDetail.getSubjectType());
                subjectJpaEntityList.add(subjectJpaEntity);
            }
        }
        subjectRepository.saveAll(subjectJpaEntityList);
    }

    public SubjectJpaEntity getBy(SubjectNumber subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    @Retryable(value = SubjectRetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 0))
    public Slice<SearchSubjectResponse> getSubjectsByKeyword(SearchSubjectRequest request, Pageable pageable) {
//        UserJpaEntity userJpaEntity = userService.getBy(request.getUserId());
//
//        List<SubjectNumber> subjectNumbers = getMySubjectsFromMySubject(userJpaEntity);
//        List<Subject> subject = subjectRepository.findByKeyword(request.getSearchQuery(), pageable);
//
//        ForkJoinPool pool = new ForkJoinPool(poolSize);
//        List<SearchSubjectResponse> results = new ArrayList<>();
//
//        try {
//            pool.submit(() -> {
//                List<SearchSubjectResponse> responses = subject.parallelStream()
//                        .map(mySubject -> {
//                            PortalResponse response = getAllSubjectsFromPortalBySubjectNumber(mySubject.getSubjectNumber());
//                            if (response.getSubjectDetails().isEmpty()) return null;
//                            else return SearchSubjectResponse.from(response.getSubjectDetails().get(0), subjectNumbers);
//                        })
//                        .collect(Collectors.toList());
//                results.addAll(responses);
//            }).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            pool.shutdown();
//        }
//
//        boolean hasNext = false;
//
//        if (results.size() > pageable.getPageSize()) {
//            results.remove(pageable.getPageSize());
//            hasNext = true;
//        }
//
//        return new SliceImpl<>(results, pageable, hasNext);
        return null;
    }

    private PortalSubjectResponse getAllSubjectsFromPortal() {
        return portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", "")
        );
    }

    private PortalSubjectResponse getAllSubjectsFromPortalBySubjectNumber(SubjectNumber subjectNumber) {
        PortalSubjectResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", subjectNumber.getValue())
        );

        if (response.getSubjectDetails().isEmpty()) {
            updatePortalSessionAndRetry();
        }


        return response;
    }

    public PortalSubjectResponse getAllSubjectsFromPortalByDepartmentAndType(Department department, Type type) {

        PortalSubjectResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody(type.getValue(), department.getValue(), "")
        );

        if (response.getSubjectDetails() == null) {
            updatePortalSessionAndRetry();
        }

        return response;
    }


    private void updatePortalSessionAndRetry() {
//        portalSessionService.updatePortalSession();

        throw new SubjectRetryException();
    }

    private static boolean isVacancy(boolean isVacancy, PortalSubjectResponse.SubjectDetail subject) {
        return !isVacancy || SubjectUtils.hasVacancy(subject.getNumberOfPeople());
    }
}
