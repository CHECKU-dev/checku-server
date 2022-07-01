package dev.checku.checkuserver.application;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "https://kuis.konkuk.ac.kr", name = "PortalFeignClient")
public interface PortalFeignClient {

    @GetMapping("/index.do")
    Response getSession();

}
