package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.notification.exception.SubjcetNotFoundException;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.dto.PortalRes;
import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.domain.subject.dao.SubjectRepository;
import dev.checku.checkuserver.domain.subject.dto.GetMySubjectDto;
import dev.checku.checkuserver.domain.subject.dto.RemoveSubjectDto;
import dev.checku.checkuserver.domain.subject.dto.SaveSubjectDto;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
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
public class SubjectService {

    private final UserService userService;
    private final SubjectRepository subjectRepository;
    private final PortalFeignClient portalFeignClient;

    public List<GetSubjectsDto.Response> getSubjectsByDepartment(
            GetSubjectsDto.Request dto,
            String session
    ) {
        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = Grade.ALL;
        Type type = Type.ALL;

        if (dto.getGrade() != null) {
            grade = Grade.valueOf(dto.getGrade());
        }
        if (dto.getType() != null && !dto.getType().equals("OTHER")) {
            type = Type.valueOf(dto.getType());
        }

        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                session,
                Values.headers,
                Values.getSubjectBody("2022", "B01012", type.getValue(), department.getValue(), ""));

        Grade finalGrade = grade;
        //TODO 정리
        return response.getBody().getSubjects()
                .stream()
                .filter(subjectDto -> finalGrade != Grade.ALL ? subjectDto.getGrade().equals(finalGrade.getValue()) : true)
                .filter(subjectDto -> (dto.getType() != null && dto.getType().equals("OTHER")) ? !subjectDto.getSubjectType().equals("전필") && !subjectDto.getSubjectType().equals("전선") : true)
                .map(GetSubjectsDto.Response::from).collect(Collectors.toList());

    }


    @Transactional
    public SaveSubjectDto.Response saveSubject(SaveSubjectDto.Request request) {

        User user = userService.getUser(request.getUserId());
        Subject subject = Subject.createSubject(request.getSubjectNumber(), user);

        Subject savedSubject = subjectRepository.save(subject);

        return SaveSubjectDto.Response.from(savedSubject);
    }

    public List<GetMySubjectDto.Response> getMySubjects(GetMySubjectDto.Request dto, String session) {

        User user = userService.getUser(dto.getUserId());
        List<Subject> mySubjects = subjectRepository.findAllByUser(user);

        return mySubjects.parallelStream()
                .map(subject -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                            session,
                            Values.headers,
                            Values.getSubjectBody("2022", "B01012", "", "", subject.getSubjectNumber()));

                    return GetMySubjectDto.Response.from(response.getBody().getSubjects().get(0));

                }).collect(Collectors.toList());
    }

    @Transactional
    public RemoveSubjectDto.Response removeSubject(RemoveSubjectDto.Request request) {

        User user = userService.getUser(request.getUserId());
        Subject subject = subjectRepository.findBySubjectNumberAndUser(request.getSubjectNumber(), user);

        subjectRepository.delete(subject);

        return RemoveSubjectDto.Response.from();

    }

    public void checkValidSubject(String subjectNumber, String session) {

        ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                session,
                Values.headers,
                Values.getSubjectBody("2022", "B01012", "", "", subjectNumber));

        try{
            PortalRes.SubjectDto subjectDto = response.getBody().getSubjects().get(0);
            subjectDto.isVacancy();
        }catch (IndexOutOfBoundsException e) {
            throw new SubjcetNotFoundException(ErrorCode.SUBJECT_NOT_FOUND);
        }

    }
}
