package dev.checku.checkuserver.checku.application;

import dev.checku.checkuserver.domain.model.Department;
import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.model.Type;
import dev.checku.checkuserver.checku.dto.SubjectDto;
import dev.checku.checkuserver.checku.dto.PortalRes;
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
        return subjectIds.parallelStream().map(id -> {
            PortalRes subject = portalFeignClient.getSubject(session,
                    "https://kuis.konkuk.ac.kr/index.do",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.80 Safari/537.36",
                    "#9e4ki",
                    "e&*\biu",
                    "W^_zie",
                    "_qw3e4",
                    "Ajd%md",
                    "ekmf3",
                    "JDow871",
                    "NuMoe6",
                    "ne+3|q",
                    "Qnd@%1",
                    "1130420",
                    "2022",
                    "B01012",
                    "",
                    "",
                    id,
                    "1",
                    "@d1#",
                    "dsParam",
                    "dm");
            return SubjectDto.Response.of(subject.getSubjects().get(0));
        }).collect(Collectors.toList());
    }


    public List<SubjectDto.Response> getSubjectsByDepartment(SubjectDto.Request dto, String session) {
        Department department = Department.valueOf(dto.getDepartment());
        Grade grade = Grade.ALL;
        Type type = Type.ALL;

        if (dto.getGrade() != null) {
            grade = Grade.valueOf(dto.getGrade());
        }
        if (dto.getType() != null && !dto.getType().equals("OTHER")) {
            type = Type.valueOf(dto.getType());
        }

        PortalRes subject = portalFeignClient.getSubject(session,
                "https://kuis.konkuk.ac.kr/index.do",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.80 Safari/537.36",
                "#9e4ki",
                "e&*\biu",
                "W^_zie",
                "_qw3e4",
                "Ajd%md",
                "ekmf3",
                "JDow871",
                "NuMoe6",
                "ne+3|q",
                "Qnd@%1",
                "1130420",
                "2022",
                "B01012",
                department.getValue(),
                type.getValue(),
                "",
                "1",
                "@d1#",
                "dsParam",
                "dm");


        Grade finalGrade = grade;

        //TODO 정리
        return subject.getSubjects()
                .stream()
                .filter(subjectDto -> finalGrade != Grade.ALL ? subjectDto.getGrade() == Integer.parseInt(finalGrade.getValue()) : true)
                .filter(subjectDto -> (dto.getType() != null && dto.getType().equals("OTHER")) ? !subjectDto.getSubjectType().equals("전필") && !subjectDto.getSubjectType().equals("전선") : true)
                .map(subjectDto -> SubjectDto.Response.of(subjectDto)).collect(Collectors.toList());

    }
}
