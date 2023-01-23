package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.enums.Department;
import dev.checku.checkuserver.domain.subject.enums.Grade;
import dev.checku.checkuserver.domain.subject.enums.SubjectType;
import dev.checku.checkuserver.domain.portal.PortalSessionService;
import dev.checku.checkuserver.domain.subject.enums.Type;
import dev.checku.checkuserver.domain.subject.repository.SubjectRepository;
import dev.checku.checkuserver.domain.subject.dto.GetSearchSubjectDto;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.domain.portal.PortalFeignClient;
import dev.checku.checkuserver.domain.portal.PortalRes;
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

import javax.validation.Valid;
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
            @Valid GetSubjectsDto.Request dto
    ) {
        User user = userService.getUserById(dto.getUserId());
        List<String> subjectList = mySubjectService.getAllSubjectsByUser(user)
                .stream().map(MySubject::getSubjectNumber).collect(Collectors.toList());

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



    @Transactional
    public void insertSubjects() {
        PortalRes subjectListDto = getAllSubjectsFromPortal();
        List<PortalRes.SubjectDto> subjects = subjectListDto.getSubjects();
        List<Subject> subjectList = new ArrayList<>();

        for (PortalRes.SubjectDto subjectDto : subjects) {
            if (subjectDto.getSubjectNumber() != null) {
                Subject subject;
                if (subjectDto.getSubjectType().equals("전선") || subjectDto.getSubjectType().equals("전필")) {
                    subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.MAJOR);
                } else {
                    subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.LIBERAL_ARTS);
                }
                subjectList.add(subject);
            }
        }

        subjectRepository.saveAll(subjectList);
    }

    public Subject getSubjectBySubjectNumber(String subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    public Slice<GetSearchSubjectDto.Response> getSubjectsByKeyword(GetSearchSubjectDto.Request dto, Pageable pageable) {
        User user = userService.getUserById(dto.getUserId());

        List<String> subjectList = mySubjectService.getAllSubjectsByUser(user).stream()
                .map(MySubject::getSubjectNumber)
                .collect(Collectors.toList());

        List<Subject> subject = subjectRepository.findSubjectByKeyword(dto.getSearchQuery(), pageable);

        List<GetSearchSubjectDto.Response> results = subject.parallelStream()
                .map(mySubject -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                            portalSessionService.getPortalSession().getSession(),
                            PortalUtils.header,
                            PortalUtils.createBody("2022", "B01012", "", "", mySubject.getSubjectNumber()));
                    return GetSearchSubjectDto.Response.from(response.getBody().getSubjects().get(0), subjectList);
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

    private PortalRes getAllSubjectsFromPortalByDepartmentAndType(Department department, Type type) {
        System.out.println("SubjectService.getAllSubjectsFromPortalByDepartmentAndType");
        System.out.println(portalSessionService.getPortalSession().getSession());

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


}
