package dev.checku.checkuserver.global.util;

import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PortalUtils {

    private final PortalSessionService portalSessionService;

    private static String ID;
    private static String PWD;

    private static final String YEAR = "2023"; // 연도
    private static final String SHTM = "B01011"; // 학기 구분

    private PortalUtils(@Value("${portal.id}") String id,
                        @Value("${portal.pwd}") String pwd,
                        PortalSessionService portalSessionService) {
        ID = id;
        PWD = pwd;
        this.portalSessionService = portalSessionService;
    }

    public static Map<String, String> header;
    public static MultiValueMap<String, String> body;

    @PostConstruct
    public void init() {
        /* 헤더 */
        header = new HashMap<>();
        header.put("Referer", "https://kuis.konkuk.ac.kr/index.do");
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.80 Safari/537.36");

        /* 바디 */
        body = new LinkedMultiValueMap<>();
        body.add("Oe2Ue", "#9e4ki");
        body.add("Le093", "e&*\biu");
        body.add("AWeh_3", "W^_zie");
        body.add("Hd,poi", "_qw3e4");
        body.add("EKf8_/", "Ajd%md");
        body.add("WEh3m", "ekmf3");
        body.add("rE\fje", "JDow871");
        body.add("JKGhe8", "NuMoe6");
        body.add("_)e7me", "ne+3|q");
        body.add("3kd3Nj", "Qnd@%1");
        body.add("@d1#SINGLE_ID", ID);
        body.add("@d1#PWD", PWD);
        body.add("@d1#default.locale", "ko");
        body.add("@d#", "@d1#");
        body.add("@d1#", "dsParam");
        body.add("@d1#tp", "dm");

        /* 세션 */
        portalSessionService.updatePortalSession();
    }

    public static MultiValueMap<String, String> createBody(
            String subjectType,
            String department, String subjectNumber
    ) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("Oe2Ue", "#9e4ki");
        body.add("Le093", "e&*\biu");
        body.add("AWeh_3", "W^_zie");
        body.add("Hd,poi", "_qw3e4");
        body.add("EKf8_/", "Ajd%md");
        body.add("WEh3m", "ekmf3");
        body.add("rE\fje", "JDow871");
        body.add("JKGhe8", "NuMoe6");
        body.add("_)e7me", "ne+3|q");
        body.add("3kd3Nj", "Qnd@%1");
        body.add("_AUTH_MENU_KEY", "1130420");
        body.add("@d1#argDeptFg", "1"); // 수강, 제청, 개설
        body.add("@d#", "@d1#");
        body.add("@d1#", "dsParam");
        body.add("@d1#tp", "dm");
        // 연도
        body.add("@d1#ltYy", YEAR);
        // 학기 구분
        body.add("@d1#ltShtm", SHTM);
        // 이수 구분
        body.add("@d1#pobtDiv", subjectType);
        // 학과 번호
        body.add("@d1#openSust", department);
        // 과목 번호
        body.add("@d1#sbjtId", subjectNumber);

        return body;
    }


}
