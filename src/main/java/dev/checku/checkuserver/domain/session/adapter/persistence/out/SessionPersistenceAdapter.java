package dev.checku.checkuserver.domain.session.adapter.persistence.out;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.session.application.port.out.UpdateSessionPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class SessionPersistenceAdapter implements UpdateSessionPort {

    private static final String PORTAL_SESSION_ID = "PORTAL_SESSION_ID";

    private final SessionRedisRepository sessionRedisRepository;

    @Override
    public void update(String sessionId) {
        sessionRedisRepository.save(new PortalSessionRedisEntity(PORTAL_SESSION_ID, sessionId));
    }
}
