package dev.checku.checkuserver.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class Values {


    private static String ID;
    private static String PWD;

    private Values(@Value("${portal.id}") String id, @Value("${portal.pwd}") String pwd) {
        Values.ID = id;
        Values.PWD = pwd;

    }

    public static Map<String, String> headers = new HashMap<>();
    public static MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    @PostConstruct
    private static Map<String, String> initHeader() {

        headers = new HashMap<>();
        headers.put("Referer", "https://kuis.konkuk.ac.kr/index.do");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.80 Safari/537.36");
        return headers;
    }

    @PostConstruct
    private static MultiValueMap<String, String> initBody() {

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
        body.add("@d1#SINGLE_ID", Values.ID);
        body.add("@d1#PWD", Values.PWD);
        body.add("@d1#default.locale", "ko");
        body.add("@d#", "@d1#");
        body.add("@d1#", "dsParam");
        body.add("@d1#tp", "dm");

        return body;
    }


}
