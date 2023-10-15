package dev.checku.checkuserver.domain.portal.application;

import dev.checku.checkuserver.domain.portal.domain.PortalSession;
import dev.checku.checkuserver.domain.portal.dto.PortalResponse;
import dev.checku.checkuserver.domain.portal.repository.SessionRedisRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.global.util.PortalRequestFactory;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortalSessionService {

    private static final String CHECKU_SESSION_ID = "CHECKU_SESSION_ID";
    private static final int WAIT_TIME = 1;
    private static final int LEASE_TIME = 3;

    private final SessionRedisRepository sessionRedisRepository;
    private final PortalLoginService portalLoginService;
    private final PortalRequestFactory portalRequestFactory;
    private final PortalFeignClient portalFeignClient;
    private final RedissonClient redissonClient;

    public void init() {
        String sessionId = portalLoginService.login();
        save(new PortalSession(CHECKU_SESSION_ID, sessionId));
    }

    public PortalSession getPortalSession() {
        return sessionRedisRepository.findById(CHECKU_SESSION_ID)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }


    public void save(PortalSession session) {
        sessionRedisRepository.save(session);
    }

    public void updatePortalSession() {
        RLock lock = redissonClient.getLock("key");

        try {
            if (!lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))
                return;

            if (isCurrentSessionIdValid()) {
                return;
            }

            String jSession = portalLoginService.login();
            save(new PortalSession(CHECKU_SESSION_ID, jSession));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private boolean isCurrentSessionIdValid() {
        PortalResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", "0001")
        );
        return response.isSuccess();
    }
}
