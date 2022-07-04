package dev.checku.checkuserver.application;

import dev.checku.checkuserver.dto.SubjectDto;
import dev.checku.checkuserver.dto.PortalRes;
import dev.checku.checkuserver.model.Department;
import dev.checku.checkuserver.model.Grade;
import dev.checku.checkuserver.model.Type;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckuService {

    @Value("${portal.id}")
    private String id;

    @Value("${portal.pwd}")
    private String pwd;

    private final PortalFeignClient portalFeignClient;

    public String getSession() {
        Response response = portalFeignClient.getSession();
        String cookie = response.headers().get("set-cookie").toString();
        String jSessionId = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        return jSessionId;
    }

    public String login() {

        String session = "JSESSIONID=" + getSession();

        ResponseEntity<String> response = portalFeignClient.login(
                session,
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
                id,
                pwd,
                "ko",
                "@d1#",
                "dsParam",
                "dm"
        );


        return session;
    }

    public List<SubjectDto.Response> getSubjects(
            List<String> subjectIds
    ) {
        String session = login();

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


    public List<SubjectDto.Response> getSubjectsByDepartment(SubjectDto.Request request) {
        String session = login();

        Department department = Department.valueOf(request.getDepartment());
        Grade grade = Grade.ALL;
        Type type = Type.ALL;

        if (request.getGrade() != null) {
            grade = Grade.valueOf(request.getGrade());
        }
        if (request.getType() != null) {
            type = Type.valueOf(request.getType());
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

        if (grade == Grade.ALL) {
            return subject.getSubjects()
                    .parallelStream()
                    .map(subjectDto -> SubjectDto.Response.of(subjectDto)).collect(Collectors.toList());
        }

        Grade finalGrade = grade;
        return subject.getSubjects()
                .parallelStream()
                .filter(subjectDto -> subjectDto.getGrade() == Integer.parseInt(finalGrade.getValue()))
                .map(subjectDto -> SubjectDto.Response.of(subjectDto)).collect(Collectors.toList());

    }
}
