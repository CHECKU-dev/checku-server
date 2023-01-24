package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.notification.exception.SubjcetNotFoundException;
import dev.checku.checkuserver.domain.notification.exception.SubjectHasVacancyException;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.enums.Department;
import dev.checku.checkuserver.domain.subject.enums.Grade;
import dev.checku.checkuserver.domain.subject.enums.SubjectType;
import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.domain.subject.enums.Type;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    public List<GetSubjectsDto.Response> getSubjectsByDepartment(
            GetSubjectsDto.Request dto
    ) {
        User user = userService.getUserById(dto.getUserId());
        List<String> subjectList = getMySubjectsFromMySubject(user);

        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = setGrade(dto.getGrade());
        Type type = setType(dto.getType());

        PortalRes response = getAllSubjectsFromPortalByDepartmentAndType(department, type); // 전필, 전선은 이미 필터링

        // OTHER은 따로 분류하지 않음 -> 따라서 애플리케이션에서 따로 구분해야함
        return response.getSubjects()
                .stream()
                .filter(subject -> filteringGrade(grade, subject)) // grade 필터링
                .filter(subject -> filteringType(type, subject)) // type 필터링 -> 전체, 기타만 분류
                .filter(subject -> isVacancy(dto.getVacancy(), subject)) // vacancy 필터링
                .map(subject -> GetSubjectsDto.Response.from(subject, subjectList)).collect(Collectors.toList());
    }

    private List<String> getMySubjectsFromMySubject(User user) {
        return mySubjectService.getAllSubjectsByUser(user)
                .stream().map(MySubject::getSubjectNumber)
                .collect(Collectors.toList());
    }

    public void checkValidSubject(String subjectNumber) {
        PortalRes response = getAllSubjectsFromPortalBySubjectNumber(subjectNumber);
        try {
            PortalRes.SubjectDto subjectDto = response.getSubjects().get(0);
            // 빈 자리 존재하는 과목인 경우 알림 제공 안됨
            if (SubjectUtils.hasVacancy(subjectDto.getNumberOfPeople())) {
                throw new SubjectHasVacancyException(ErrorCode.SUBJECT_HAS_VACANCY);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SubjcetNotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }
    }


    @Transactional
    public void insertSubjects() {
        PortalRes subjectListDto = getAllSubjectsFromPortal();
        List<PortalRes.SubjectDto> subjects = subjectListDto.getSubjects();
        List<Subject> subjectList = new ArrayList<>();

        for (PortalRes.SubjectDto subjectDto : subjects) {
            if (subjectDto.getSubjectNumber() != null) {
                Subject subject = classifyMajorOrLiberalArts(subjectDto);
                ;
                subjectList.add(subject);
            }
        }
        subjectRepository.saveAll(subjectList);
    }

    private static Subject classifyMajorOrLiberalArts(PortalRes.SubjectDto subjectDto) {
        Subject subject;
        if (isMajor(subjectDto)) {
            subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.MAJOR);
        } else {
            subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.LIBERAL_ARTS);
        }
        return subject;
    }


    public Subject getSubjectBySubjectNumber(String subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }

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
                PortalUtils.createBody("2022", "B01012", "", "", "")
        );

        return response.getBody();
    }

    private PortalRes getAllSubjectsFromPortalBySubjectNumber(String subjectNumber) {
        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody("2022", "B01012", "", "", subjectNumber)
        );

        return response.getBody();
    }

    private PortalRes getAllSubjectsFromPortalByDepartmentAndType(Department department, Type type) {

        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody("2022", "B01012", type.getValue(), department.getValue(), "")
        );

        return response.getBody();
    }


    private Type setType(String type) {
        if (!StringUtils.hasText(type)) {
            return Type.ALL;
        }

        return Type.valueOf(type);

    }

    private Grade setGrade(String grade) {
        if (StringUtils.hasText(grade)) {
            return Grade.valueOf(grade.toUpperCase());
        }
        return Grade.ALL;
    }

    private static boolean isVacancy(boolean isVacancy, PortalRes.SubjectDto subject) {
        return !isVacancy ? true : SubjectUtils.hasVacancy(subject.getNumberOfPeople());
    }

    private static boolean filteringType(Type type, PortalRes.SubjectDto subject) {
        if (type == Type.ALL) return true; // 전체는 필터링 X
        else if (type == Type.ESSENTIAL || type == Type.OPTIONAL) return true; // 이미 정렬

        return !subject.getSubjectType().equals("전필") && !subject.getSubjectType().equals("전선");
    }

    private static boolean filteringGrade(Grade grade, PortalRes.SubjectDto subject) {
        if (grade == Grade.ALL) return true;
        return grade.getGrade().equals(subject.getGrade());
    }

    private static boolean isMajor(PortalRes.SubjectDto subjectDto) {
        return "전선".equals(subjectDto.getSubjectType()) || "전필".equals(subjectDto.getSubjectType());
    }


}
