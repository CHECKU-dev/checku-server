package dev.checku.checkuserver.domain.portal.application;

import dev.checku.checkuserver.domain.portal.domain.PortalSession;
import dev.checku.checkuserver.domain.portal.repository.SessionRedisRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.global.util.PortalUtils;
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

    private final String SESSION = "CHECKU_SESSION_ID";
    private final SessionRedisRepository sessionRedisRepository;
    private final PortalLoginService portalLoginService;
    private final PortalFeignClient portalFeignClient;
    private final RedissonClient redissonClient;

    public void init() {
        String jSession = portalLoginService.login();
        savePortalSession(new PortalSession(SESSION, jSession));
    }

    public PortalSession getPortalSession() {
        return sessionRedisRepository.findById(SESSION)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }


    public void savePortalSession(PortalSession session) {
        sessionRedisRepository.save(session);
    }

    public void updatePortalSession() {
        RLock lock = redissonClient.getLock("key");
        try {
            if (!lock.tryLock(1, 3, TimeUnit.SECONDS))
                return;

            if (portalFeignClient.test(getPortalSession().getSession(), PortalUtils.header, PortalUtils.createBody("", "", "0001")).getBody().getSubjects() != null){
                return;
            }

            String jSession = portalLoginService.login();
            savePortalSession(new PortalSession(SESSION, jSession));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


}
