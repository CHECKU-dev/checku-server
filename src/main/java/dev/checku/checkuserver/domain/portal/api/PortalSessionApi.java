package dev.checku.checkuserver.domain.portal.api;

import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal-session")
public class PortalSessionApi {

    private final PortalSessionService portalSessionService;

    @GetMapping
    public void updateSession() {
        portalSessionService.updatePortalSession();
    }

}
