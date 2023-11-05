package dev.checku.checkuserver.domain.portal.adapter.out;

import dev.checku.checkuserver.domain.portal.adapter.in.web.PortalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient(url = "https://kuis.konkuk.ac.kr", name = "checku")
public interface PortalFeignClient {

    @GetMapping("/index.do")
    ResponseEntity<String> index();

    @PostMapping(value = "/Login/login.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> login(
            @RequestHeader Map<String, String> header,
            MultiValueMap<String, String> body
    );

    @PostMapping(value = "/CourTotalTimetableInq/find.do", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    PortalResponse getSubjects(
            @RequestHeader Map<String, String> header,
            MultiValueMap<String, String> body);
}


