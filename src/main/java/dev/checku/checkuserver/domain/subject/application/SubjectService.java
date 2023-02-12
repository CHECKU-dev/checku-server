package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.notification.exception.SubjectNotFoundException;
import dev.checku.checkuserver.domain.notification.exception.SubjectHasVacancyException;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.enums.Department;
import dev.checku.checkuserver.domain.subject.enums.Grade;
import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.domain.subject.enums.Type;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.subject.repository.SubjectRepository;
import dev.checku.checkuserver.domain.subject.dto.GetSearchSubjectDto;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.domain.portal.application.PortalFeignClient;
import dev.checku.checkuserver.domain.portal.dto.PortalRes;
import dev.checku.checkuserver.global.util.PortalUtils;
import dev.checku.checkuserver.global.util.SubjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;
    private final MySubjectService mySubjectService;
    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final SubjectRepository subjectRepository;

    @Retryable(value = SubjectRetryException.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public List<GetSubjectsDto.Response> getSubjectsByDepartment(GetSubjectsDto.Request dto) {
        User user = userService.getUserById(dto.getUserId());
        List<String> subjectList = getMySubjectsFromMySubject(user);

        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = Grade.setGrade(dto.getGrade());
        Type type = Type.setType(dto.getType());
        Boolean inVacancy = dto.getVacancy();

        PortalRes response = getAllSubjectsFromPortalByDepartmentAndType(department, type); // 전필, 전선은 이미 필터링

        // OTHER은 따로 분류하지 않음 -> 따라서 애플리케이션에서 따로 구분해야함
        return response.getSubjects()
                .stream()
                .filter(subject -> grade.matchGrade(subject.getGrade()))
                .filter(subject -> type.matchType(subject.getSubjectType()))
                .filter(subject -> isVacancy(inVacancy, subject)) // vacancy 필터링
                .map(subject -> GetSubjectsDto.Response.from(subject, subjectList)).collect(Collectors.toList());
    }

//    @Recover
//    public List<GetSubjectsDto.Response> recover(SubjectRetryException e, GetSubjectsDto.Request dto) {
//        return null;
//    }

    private List<String> getMySubjectsFromMySubject(User user) {
        return mySubjectService.getAllSubjectsByUser(user)
                .stream().map(MySubject::getSubjectNumber)
                .collect(Collectors.toList());
    }


    @Retryable(value = SubjectRetryException.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public void checkValidSubject(String subjectNumber) {
        PortalRes response = getAllSubjectsFromPortalBySubjectNumber(subjectNumber);
        try {
            PortalRes.SubjectDto subjectDto = response.getSubjects().get(0);
            // 빈 자리 존재하는 과목인 경우 알림 제공 안됨
            if (SubjectUtils.hasVacancy(subjectDto.getNumberOfPeople())) {
                throw new SubjectHasVacancyException(ErrorCode.SUBJECT_HAS_VACANCY);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SubjectNotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }
    }


    @Transactional
    public void insertSubjects() {
        PortalRes subjectListDto = getAllSubjectsFromPortal();
        List<PortalRes.SubjectDto> subjects = subjectListDto.getSubjects();
        List<Subject> subjectList = new ArrayList<>();

        for (PortalRes.SubjectDto subjectDto : subjects) {
            if (subjectDto.getSubjectNumber() != null) {
                Subject subject = Subject.classifyMajorOrLiberalArts(subjectDto.getSubjectNumber(), subjectDto.getName(), subjectDto.getSubjectType());
                subjectList.add(subject);
            }
        }
        subjectRepository.saveAll(subjectList);
    }

    public Subject getSubjectBySubjectNumber(String subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    @Retryable(value = SubjectRetryException.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public Slice<GetSearchSubjectDto.Response> getSubjectsByKeyword(GetSearchSubjectDto.Request dto, Pageable pageable) {
        User user = userService.getUserById(dto.getUserId());

        List<String> subjectList = getMySubjectsFromMySubject(user);
        List<Subject> subject = subjectRepository.findSubjectByKeyword(dto.getSearchQuery(), pageable);

        List<GetSearchSubjectDto.Response> results = subject.parallelStream()
                .map(mySubject -> {
                    PortalRes response = getAllSubjectsFromPortalBySubjectNumber(mySubject.getSubjectNumber());
                    return GetSearchSubjectDto.Response.from(response.getSubjects().get(0), subjectList);
                })
                .collect(Collectors.toList());

        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private PortalRes getAllSubjectsFromPortal() {

        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody("", "", "")
        );

        return response.getBody();
    }

    private PortalRes getAllSubjectsFromPortalBySubjectNumber(String subjectNumber) {
        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody("", "", subjectNumber)
        );

        if (response.getBody().getSubjects() == null) {
            updatePortalSessionAndRetry();
        }


        return response.getBody();
    }

    public PortalRes getAllSubjectsFromPortalByDepartmentAndType(Department department, Type type) {

        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody(type.getValue(), department.getValue(), "")
        );
        if (response.getBody().getSubjects() == null) {
            updatePortalSessionAndRetry();
        }

        return response.getBody();
    }


    private void updatePortalSessionAndRetry() {
        portalSessionService.updatePortalSession();
        throw new SubjectRetryException();
    }

    private static boolean isVacancy(boolean isVacancy, PortalRes.SubjectDto subject) {
        return !isVacancy || SubjectUtils.hasVacancy(subject.getNumberOfPeople());
    }


}
