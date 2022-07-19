package dev.checku.checkuserver.checku.application;

import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.checku.dto.SubjectDto;
import dev.checku.checkuserver.checku.dto.PortalRes;
import dev.checku.checkuserver.global.util.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckuService {
    private final PortalFeignClient portalFeignClient;

    public List<SubjectDto.Response> getSubjects(
            List<String> subjectIds,
            String session
    ) {
        return subjectIds.stream().map(subjectId -> {

            Values.updateSubjectBody("2022", "B01012", "", "", subjectId);
            PortalRes subject = portalFeignClient.getSubject(
                    session,
                    Values.headers,
                    Values.subjectBody);

            return SubjectDto.Response.of(subject.getSubjects().get(0));
        }).collect(Collectors.toList());
    }


    public List<SubjectDto.Response> getSubjectsByDepartment(
            SubjectDto.Request dto,
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

        Values.updateSubjectBody("2022", "B01012", type.getValue(), department.getValue(), "");

        PortalRes subject = portalFeignClient.getSubject(
                session,
                Values.headers,
                Values.subjectBody);

        Grade finalGrade = grade;
        //TODO 정리
        return subject.getSubjects()
                .stream()
                .filter(subjectDto -> finalGrade != Grade.ALL ? subjectDto.getGrade() == Integer.parseInt(finalGrade.getValue()) : true)
                .filter(subjectDto -> (dto.getType() != null && dto.getType().equals("OTHER")) ? !subjectDto.getSubjectType().equals("전필") && !subjectDto.getSubjectType().equals("전선") : true)
                .map(subjectDto -> SubjectDto.Response.of(subjectDto)).collect(Collectors.toList());

    }
}
