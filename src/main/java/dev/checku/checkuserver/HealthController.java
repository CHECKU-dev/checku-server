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

    @GetMapping("")
    public String healthCheck() {

        for (int i = 0; i < 100; i++) {
            log.info("message Info");
//            log.warn("message Warn");
//            log.error("message Error");
        }



        return "I'm OK...";
    }

}
