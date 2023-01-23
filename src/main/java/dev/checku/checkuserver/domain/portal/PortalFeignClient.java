package dev.checku.checkuserver.domain.portal;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient(url = "https://kuis.konkuk.ac.kr", name = "kuis.konkuk.ac.kr")
public interface PortalFeignClient {

    @GetMapping("/index.do")
    Response getSession();

    @PostMapping(value = "/Login/login.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> login(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader Map<String, String> header,
            MultiValueMap<String, String> request
    );

    @PostMapping(value = "/CourTotalTimetableInq/find.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<PortalRes> getSubjects(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader Map<String, String> header,
            MultiValueMap<String, String> subjectBody);


//    @PostMapping(value = "/CourTotalTimetableInq/find.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    ResponseEntity<PortalRes> getSubjects(
//            @RequestHeader("Cookie") String cookie,
//            @RequestHeader Map<String, String> header,
//            MultiValueMap<String, String> subjectBody);



}


