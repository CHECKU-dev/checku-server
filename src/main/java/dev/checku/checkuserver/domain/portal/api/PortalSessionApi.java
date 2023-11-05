package dev.checku.checkuserver.domain.portal.api;

import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.global.advice.InternalApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal-session")
public class PortalSessionApi {

    private final PortalSessionService portalSessionService;

    @InternalApi
    @PatchMapping
    public void updateSessionId() {
//        portalSessionService.updatePortalSession();
    }
}
