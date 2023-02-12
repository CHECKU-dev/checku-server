package dev.checku.checkuserver;

import dev.checku.checkuserver.domain.portal.application.PortalLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public String healthCheck() {
        return "I'm OK...";
    }


}
