package dev.checku.checkuserver.application;

import dev.checku.checkuserver.dto.SubjectDto;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


        System.out.println(response.getBody());

        return session;
    }

    public List<SubjectListDto.SubjectDto> getSubjects(
            List<String> subjectIds
    ) {
        String session = login();

        return subjectIds.parallelStream().map(id -> {
            SubjectListDto subject = portalFeignClient.getSubject(session,
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
            return subject.getSubjects().get(0);
        }).collect(Collectors.toList());
    }


    public List<SubjectListDto.SubjectDto> getAllSubject(SubjectDto.Request request) {
        String session = login();

        SubjectListDto subject = portalFeignClient.getSubject(session,
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
                request.getDepartment(),
                request.getType(),
                "",
                "1",
                "@d1#",
                "dsParam",
                "dm");

        List<SubjectListDto.SubjectDto> collect = subject.getSubjects().parallelStream().filter(new Predicate<SubjectListDto.SubjectDto>() {
            @Override
            public boolean test(SubjectListDto.SubjectDto subjectDto) {
                return subjectDto.getGrade() == Integer.parseInt(request.getGrade());
            }
        }).collect(Collectors.toList());

        return collect;

    }
}
