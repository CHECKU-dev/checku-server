package dev.checku.checkuserver.domain.portal.application;

import dev.checku.checkuserver.domain.portal.domain.PortalSession;
import dev.checku.checkuserver.domain.portal.repository.SessionRedisRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortalSessionService {

    private final String SESSION = "CHECKU_SESSION_ID";
    private final SessionRedisRepository sessionRedisRepository;
    private final PortalLoginService portalLoginService;

    public void init() {
        String jSessionId = portalLoginService.login();
        savePortalSession(new PortalSession(SESSION, jSessionId));
    }

    public PortalSession getPortalSession() {
        return sessionRedisRepository.findById(SESSION)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }

    public void savePortalSession(PortalSession session) {
        sessionRedisRepository.save(session);
    }

    public void updatePortalSession(String value) {
        savePortalSession(new PortalSession(SESSION, value));
    }

    @Scheduled(cron = "0 0/59 * * * *")
    public void refreshJsessionid() {
        if (!portalLoginService.login().isBlank()) {
            updatePortalSession(portalLoginService.login());
        }
    }


}
