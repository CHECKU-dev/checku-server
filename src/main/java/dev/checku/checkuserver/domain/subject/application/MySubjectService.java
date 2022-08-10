package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.notification.exception.HaveAVacancyException;
import dev.checku.checkuserver.domain.notification.exception.SubjcetNotFoundException;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.dto.PortalRes;
import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.domain.subject.dao.MySubjectRepository;
import dev.checku.checkuserver.domain.subject.dto.GetMySubjectDto;
import dev.checku.checkuserver.domain.subject.dto.RemoveSubjectRequest;
import dev.checku.checkuserver.domain.subject.dto.SaveSubjectRequest;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.global.util.SubjectUtil;
import dev.checku.checkuserver.global.util.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySubjectService {

    private final UserService userService;
    private final MySubjectRepository mySubjectRepository;
    private final PortalFeignClient portalFeignClient;

    public List<GetSubjectsDto.Response> getSubjectsByDepartment(
            GetSubjectsDto.Request dto,
            String session
    ) {
        User user = userService.getUser(dto.getUserId());
        List<String> subjectList = getAllSubjectByUser(user)
                .stream().map(MySubject::getSubjectNumber).collect(Collectors.toList());

        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = Grade.ALL;
        Type type = Type.ALL;
        boolean isVacancy = false;

        if (dto.getGrade() != null) {
            grade = Grade.valueOf(dto.getGrade());
        }
        if (dto.getType() != null && !dto.getType().equals("OTHER")) {
            type = Type.valueOf(dto.getType());
        }
        if (dto.getIsVacancy() != null) {
            isVacancy = true;
        }

        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                session,
                Values.headers,
                Values.getSubjectBody("2022", "B01012", type.getValue(), department.getValue(), ""));

        //TODO 정리
        Grade finalGrade = grade;
        Boolean finalIsVacancy = isVacancy;
        return response.getBody().getSubjects()
                .stream()
                .filter(subjectDto -> finalGrade != Grade.ALL ? subjectDto.getGrade().equals(finalGrade.getValue()) : true)
                .filter(subjectDto -> (dto.getType() != null && dto.getType().equals("OTHER")) ? !subjectDto.getSubjectType().equals("전필") && !subjectDto.getSubjectType().equals("전선") : true)
                .filter(subjectDto -> finalIsVacancy ? SubjectUtil.isVacancy(subjectDto.getNumberOfPeople()) : true)
                .map(subject -> GetSubjectsDto.Response.from(subject, subjectList)).collect(Collectors.toList());

    }


    @Transactional
    public void saveOrRemoveSubject(SaveSubjectRequest request) {

        User user = userService.getUser(request.getUserId());
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

    public List<GetMySubjectDto.Response> getMySubjects(GetMySubjectDto.Request dto, String session) {

        User user = userService.getUser(dto.getUserId());
        List<MySubject> myMySubjects = mySubjectRepository.findAllByUser(user);

        return myMySubjects.parallelStream()
                .map(mySubject -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                            session,
                            Values.headers,
                            Values.getSubjectBody("2022", "B01012", "", "", mySubject.getSubjectNumber()));

                    return GetMySubjectDto.Response.from(response.getBody().getSubjects().get(0));

                }).collect(Collectors.toList());
    }

    @Transactional
    public void removeSubject(RemoveSubjectRequest request) {

        User user = userService.getUser(request.getUserId());
        MySubject mySubject = getMySubject(request.getSubjectNumber(), user);

        mySubjectRepository.delete(mySubject);

    }

    public void checkValidSubject(String subjectNumber, String session) {

        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                session,
                Values.headers,
                Values.getSubjectBody("2022", "B01012", "", "", subjectNumber));

        try {
            PortalRes.SubjectDto subjectDto = response.getBody().getSubjects().get(0);

            // 빈 자리 존재하는 과목인 경우 알림 제공 안됨
            if (SubjectUtil.isVacancy(subjectDto.getNumberOfPeople())) {
                throw new HaveAVacancyException(ErrorCode.HAVA_A_VACANCY);
            }
//            subjectDto.isVacancy();
        } catch (IndexOutOfBoundsException e) {
            throw new SubjcetNotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }

    }

    public List<MySubject> getAllSubjectByUser(User user) {
        return mySubjectRepository.findAllByUser(user);
    }
}
