package dev.checku.checkuserver.domain.portal.application;

import dev.checku.checkuserver.domain.portal.domain.PortalSession;
import dev.checku.checkuserver.domain.portal.repository.SessionRedisRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortalSessionService {

    private final String SESSION = "CHECKU_SESSION_ID";
    private final SessionRedisRepository sessionRedisRepository;
    private final PortalLoginService portalLoginService;

    public PortalSession getPortalSession() {
        return sessionRedisRepository.findById(SESSION)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }

    private void savePortalSession(PortalSession session) {
        sessionRedisRepository.save(session);
    }

    public void updatePortalSession() {
        String jSession = portalLoginService.login();
        savePortalSession(new PortalSession(SESSION, jSession));
    }


}
