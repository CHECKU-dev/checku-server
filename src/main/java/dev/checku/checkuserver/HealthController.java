package dev.checku.checkuserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("")
    public String healthCheck() {
        return "I'm OK";
    }

}
