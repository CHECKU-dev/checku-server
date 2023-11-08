package dev.checku.checkuserver.domain.session.adapter.out.persistence;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.session.application.port.out.GetPortalSessionPort;
import dev.checku.checkuserver.domain.session.application.port.out.UpdateSessionPort;
import dev.checku.checkuserver.domain.session.domain.PortalSession;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PortalPortalSessionPersistenceAdapter implements UpdateSessionPort, GetPortalSessionPort {

    private final PortalSessionMapper portalSessionMapper;

    private static final String PORTAL_SESSION_ID = "PORTAL_SESSION_ID";

    private final PortalSessionRedisRepository portalSessionRedisRepository;

    @Override
    public void update(String sessionId) {
        portalSessionRedisRepository.save(new PortalSessionRedisEntity(PORTAL_SESSION_ID, sessionId));
    }

    @Override
    public PortalSession get() {
        PortalSessionRedisEntity portalSessionRedisEntity = portalSessionRedisRepository.findById(PORTAL_SESSION_ID)
                .orElseThrow(() -> new PortalSessionRedisEntityNotFoundException());
        return portalSessionMapper.mapToDomainEntity(portalSessionRedisEntity);
    }
}
