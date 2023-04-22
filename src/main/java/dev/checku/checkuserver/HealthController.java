package dev.checku.checkuserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public String healthCheck() {
        log.info("this log is info");
        log.warn("this log is warn");
        log.error("this log is error");
        return "I'm OK...";
    }


}
