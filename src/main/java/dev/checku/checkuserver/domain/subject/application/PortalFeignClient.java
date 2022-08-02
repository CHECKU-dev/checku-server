package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.subject.dto.PortalRes;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(url = "https://kuis.konkuk.ac.kr", name = "kuis.konkuk.ac.kr")
public interface PortalFeignClient {

    @GetMapping("/index.do")
    Response getSession();

    @PostMapping(value = "/Login/login.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> login(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader Map<String, String> headers,
            MultiValueMap<String, String> request
    );

    @PostMapping(value = "/CourTotalTimetableInq/find.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<PortalRes> getSubject(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader Map<String, String> headers,
            MultiValueMap<String, String> subjectBody);

    @PostMapping(value = "/CourTotalTimetableInq/find.do", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<PortalRes> getSubjects(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader("Referer") String referer,
            @RequestHeader("User-Agent") String userAgent,
            @RequestPart("Oe2Ue") String Oe,
            @RequestPart("Le093") String Le,
            @RequestPart("AWeh_3") String AW,
            @RequestPart("Hd,poi") String Hd,
            @RequestPart("EKf8_/") String Ek,
            @RequestPart("WEh3m") String WE,
            @RequestPart("rE\fje") String rE,
            @RequestPart("JKGhe8") String JK,
            @RequestPart("_)e7me") String e7,
            @RequestPart("3kd3Nj") String kd,
            @RequestPart("@d#") String d,
            @RequestPart("@d1") String d1,
            @RequestPart("@d1#tp") String tp,
            @RequestPart("_AUTH_MENU_KEY") String authMenuKey,
            @RequestPart("@d1#ltYy") String ltYy,
            @RequestPart("@d1#ltShtm") String ltShtm,
            @RequestPart("@d1#openSust") String openSust,
            @RequestPart("@d1#pobtDiv") String pobtDiv,
            @RequestPart("@d1#sbjtId") String sbjtId,
            @RequestPart("@d1#corsKorNm") String corsKorNm,
            @RequestPart("@d1#sprfNo") String sprfNo,
            @RequestPart("@d1#argDeptFg") String argDeptFg,
            @RequestPart("@d1#arglangNm") String arglangNm
    );


}


