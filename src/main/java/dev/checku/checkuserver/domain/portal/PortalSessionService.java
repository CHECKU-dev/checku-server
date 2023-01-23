package dev.checku.checkuserver.domain.portal;

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

    private final SessionRedisRepository sessionRedisRepository;
    private final LoginService loginService;

    public void init() {
        String jSessionId = loginService.login();
        savePortalSession(new PortalSession(SessionConst.SESSION, jSessionId));
    }

    public PortalSession getPortalSession() {
        return sessionRedisRepository.findById(SessionConst.SESSION)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }

    public void savePortalSession(PortalSession session) {
        sessionRedisRepository.save(session);
    }

    public void updatePortalSession(String value) {
        savePortalSession(new PortalSession(SessionConst.SESSION, value));
    }

    @Scheduled(cron = "0 0/59 * * * *")
    public void refreshJsessionid() {
        if (!loginService.login().isBlank()) {
            updatePortalSession(loginService.login());
        }
    }


}
