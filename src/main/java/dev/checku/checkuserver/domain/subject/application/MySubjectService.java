package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.domain.notification.exception.SubjcetNotFoundException;
import dev.checku.checkuserver.domain.notification.exception.SubjectHasVacancyException;
import dev.checku.checkuserver.domain.subject.dto.GetMySubjectDto;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.dto.RemoveSubjectReq;
import dev.checku.checkuserver.domain.subject.dto.SaveSubjectReq;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.subject.repository.MySubjectRepository;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.infra.portal.LoginService;
import dev.checku.checkuserver.infra.portal.PortalFeignClient;
import dev.checku.checkuserver.infra.portal.PortalRes;
import dev.checku.checkuserver.global.util.SubjectUtils;
import dev.checku.checkuserver.global.util.PortalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySubjectService {

    private final LoginService loginService;
    private final UserService userService;
    private final MySubjectRepository mySubjectRepository;
    private final PortalFeignClient portalFeignClient;

    public List<GetSubjectsDto.Response> getSubjectsByDepartment(
            GetSubjectsDto.Request dto
    ) {
        User user = userService.getUserById(dto.getUserId());
        List<String> subjectList = getAllSubjectsByUser(user)
                .stream().map(MySubject::getSubjectNumber).collect(Collectors.toList());

        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = Grade.ALL;
        Type type = Type.ALL;
        boolean isVacancy = false;

        if (dto.getGrade() != null) {
            grade = Grade.valueOf(dto.getGrade().toUpperCase());
        }
        if (dto.getType() != null && !dto.getType().equals("OTHER")) {
            type = Type.valueOf(dto.getType());
        }
        if (dto.getVacancy() != null && dto.getVacancy()) {
            isVacancy = true;
        }

        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                PortalUtils.JSESSIONID,
                PortalUtils.header,
                PortalUtils.createBody("2022", "B01012", type.getValue(), department.getValue(), "")
        );

        //TODO 정리
        Grade finalGrade = grade;
        boolean finalIsVacancy = isVacancy;
        return response.getBody().getSubjects()
                .stream()
                .filter(subjectDto -> finalGrade == Grade.ALL || subjectDto.getGrade().equals(finalGrade.getGrade()))
                .filter(subjectDto -> dto.getType() == null || !dto.getType().equals("OTHER") || !subjectDto.getSubjectType().equals("전필") && !subjectDto.getSubjectType().equals("전선"))
                .filter(subjectDto -> finalIsVacancy ? SubjectUtils.hasVacancy(subjectDto.getNumberOfPeople()) : true)
                .map(subject -> GetSubjectsDto.Response.from(subject, subjectList)).collect(Collectors.toList());
    }


    @Transactional
    public void saveOrRemoveSubject(SaveSubjectReq request) {

        User user = userService.getUserById(request.getUserId());
        // 삭제
        if (mySubjectRepository.existsBySubjectNumberAndUser(request.getSubjectNumber(), user)) {
            MySubject mySubject = getMySubject(request.getSubjectNumber(), user);
            mySubjectRepository.delete(mySubject);
        }
        // 추가
        else {
            MySubject mySubject = MySubject.createSubject(request.getSubjectNumber(), user);
            mySubjectRepository.save(mySubject);

        }


    }

    public MySubject getMySubject(String subjectNumber, User user) {
        return mySubjectRepository.findBySubjectNumberAndUser(subjectNumber, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MY_SUBJECT_NOT_FOUND));
    }

    public List<GetMySubjectDto.Response> getMySubjects(GetMySubjectDto.Request dto) {

        System.out.println("getMySubjects");

        User user = userService.getUserById(dto.getUserId());
        List<MySubject> myMySubjects = mySubjectRepository.findAllByUser(user);

        return myMySubjects.parallelStream()
                .map(mySubject -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                            PortalUtils.JSESSIONID,
                            PortalUtils.header,
                            PortalUtils.createBody("2022", "B01012", "", "", mySubject.getSubjectNumber()));

                    return GetMySubjectDto.Response.from(response.getBody().getSubjects().get(0));
                }).collect(Collectors.toList());

        /* 디버그용 */
//        return myMySubjects.parallelStream()
//                .map(mySubject -> {
//                    ResponseEntity<String> response = portalFeignClient.getSubject2(
//                            PortalUtils.JSESSIONID,
//                            PortalUtils.header,
//                            PortalUtils.createBody("2022", "B01012", "", "", mySubject.getSubjectNumber()));
//
//                    System.out.println(response.getBody());
//
////                    return GetMySubjectDto.Response.from(response.getBody().getSubjects().get(0));
//
//                    return null;
//                }).collect(Collectors.toList());
    }

    @Transactional
    public void removeSubject(RemoveSubjectReq request) {

        User user = userService.getUserById(request.getUserId());
        MySubject mySubject = getMySubject(request.getSubjectNumber(), user);

        mySubjectRepository.delete(mySubject);

    }

    public void checkValidSubject(String subjectNumber) {
        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                PortalUtils.JSESSIONID,
                PortalUtils.header,
                PortalUtils.createBody("2022", "B01012", "", "", subjectNumber)
        );

        try {
            PortalRes.SubjectDto subjectDto = Objects.requireNonNull(response.getBody()).getSubjects().get(0);
            // 빈 자리 존재하는 과목인 경우 알림 제공 안됨
            if (SubjectUtils.hasVacancy(subjectDto.getNumberOfPeople())) {
                throw new SubjectHasVacancyException(ErrorCode.SUBJECT_HAS_VACANCY);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new SubjcetNotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }
    }

    public List<MySubject> getAllSubjectsByUser(User user) {
        return mySubjectRepository.findAllByUser(user);
    }

}
