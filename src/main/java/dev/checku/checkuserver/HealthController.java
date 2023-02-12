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
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    private final PortalLoginService portalLoginService;

    @GetMapping
    public String healthCheck() {
        String login = portalLoginService.login();
        System.out.println(login);

        return "I'm OK...";
    }


}
