package dev.checku.checkuserver.application;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckuService {

    private final PortalFeignClient portalFeignClient;

    public String getSession() {
        Response response = portalFeignClient.getSession();
        String cookie = response.headers().get("set-cookie").toString();
        String jSessionId = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        System.out.println(jSessionId);
        return jSessionId;
    }

    public String getCookie() {

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
                "dudwls143",
                "@dudwlsdl12",
                "ko",
                "@d1#",
                "dsParam",
                "dm"
        );

        System.out.println(response.getBody());

        return session;
//        System.out.println(cookie.headers().get("co"));
    }

    public void getSubjects(
            Long subjectId
    ) {
        String session = getCookie();

    }


}
