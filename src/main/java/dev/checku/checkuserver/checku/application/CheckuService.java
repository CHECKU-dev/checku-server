package dev.checku.checkuserver.checku.application;

import dev.checku.checkuserver.checku.dto.SaveSubjectRequest;
import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.checku.dto.GetSubjectDto;
import dev.checku.checkuserver.checku.dto.PortalRes;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.util.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckuService {

    private final PortalFeignClient portalFeignClient;
    private final UserService userService;


    public List<GetSubjectDto.Response> getSubjects(
            List<String> subjectIds,
            String session
    ) {
        return subjectIds.parallelStream()
                .map(subjectId -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                            session,
                            Values.headers,
                            Values.getSubjectBody("2022", "B01012", "", "", subjectId));

                    return GetSubjectDto.Response.from(response.getBody().getSubjects().get(0));

                }).collect(Collectors.toList());

    }


    public List<GetSubjectDto.Response> getSubjectsByDepartment(
            GetSubjectDto.Request dto,
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

//        Values.updateSubjectBody("2022", "B01012", type.getValue(), department.getValue(), "");

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
                .map(GetSubjectDto.Response::from).collect(Collectors.toList());

    }


}
