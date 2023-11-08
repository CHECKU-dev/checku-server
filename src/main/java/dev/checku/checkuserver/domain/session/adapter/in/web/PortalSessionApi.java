package dev.checku.checkuserver.domain.session.adapter.in.web;

import dev.checku.checkuserver.domain.session.application.port.in.UpdatePortalSessionUseCase;
import dev.checku.checkuserver.domain.session.application.service.PortalSessionService;
import dev.checku.checkuserver.global.advice.InternalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;


@InternalApi
@RestController
@RequiredArgsConstructor
public class PortalSessionApi {

    private final PortalSessionService portalSessionService;
    private final UpdatePortalSessionUseCase updatePortalSessionUseCase;

    @PatchMapping("/api/portal/session")
    public void updateSessionId() {
        updatePortalSessionUseCase.update();
    }
}
