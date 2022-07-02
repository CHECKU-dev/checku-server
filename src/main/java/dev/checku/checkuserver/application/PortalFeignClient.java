package dev.checku.checkuserver.application;

import feign.Response;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.Encoder;
import java.util.Map;

@FeignClient(url = "https://kuis.konkuk.ac.kr", name = "kuis.konkuk.ac.kr")
public interface PortalFeignClient {

    @GetMapping("/index.do")
    Response getSession();

    @PostMapping(value = "/Login/login.do", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<String> login(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader("Referer") String referer,
            @RequestHeader("User-Agent") String userAgent,
            @RequestPart("Oe2Ue") String Oe2Ue,
            @RequestPart("Le093") String Le,
            @RequestPart("AWeh_3") String AW,
            @RequestPart("Hd,poi") String Hd,
            @RequestPart("EKf8_/") String Ek,
            @RequestPart("WEh3m") String WE,
            @RequestPart("rE\fje") String rE,
            @RequestPart("JKGhe8") String JK,
            @RequestPart("_)e7me") String e7,
            @RequestPart("3kd3Nj") String k3,
            @RequestPart("@d1#SINGLE_ID") String id,
            @RequestPart("@d1#PWD") String pwd,
            @RequestPart("@d1#default.locale") String locale,
            @RequestPart("@d#") String d,
            @RequestPart("@d1#") String d1,
            @RequestPart("@d1#tp") String d2);


//    @PostMapping(value = "/Login/login.do", cosumes = "application/x-www-form-urlencoded")
//    ResponseEntity<String> login2(
//            @RequestHeader("Cookie") String cookie,
//            @RequestHeader("Referer") String referer,
//            @RequestHeader("User-Agent") String userAgent,
//            @RequestBody LoginRequest loginRequest);



    @PostMapping(value = "/CourTotalTimetableInq/find.do", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<String> getSubject(
            @RequestHeader("Cookie") String cookie,
            @RequestHeader("Referer") String referer,
            @RequestHeader("User-Agent") String userAgent,
            @RequestPart("Oe2Ue") String Oe2Ue,
            @RequestPart("Le093") String Le,
            @RequestPart("AWeh_3") String AW,
            @RequestPart("Hd,poi") String Hd,
            @RequestPart("EKf8_/") String Ek,
            @RequestPart("WEh3m") String WE,
            @RequestPart("rE\fje") String rE,
            @RequestPart("JKGhe8") String JK,
            @RequestPart("_)e7me") String e7,
            @RequestPart("3kd3Nj") String k3,
            @RequestPart("@d1#SINGLE_ID") String id,
            @RequestPart("@d1#PWD") String pwd,
            @RequestPart("@d1#default.locale") String locale,
            @RequestPart("@d#") String d,
            @RequestPart("@d1#") String d1,
            @RequestPart("@d1#tp") String d2);


}


