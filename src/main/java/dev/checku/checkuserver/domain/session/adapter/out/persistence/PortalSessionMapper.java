package dev.checku.checkuserver.domain.session.adapter.out.persistence;

import dev.checku.checkuserver.domain.session.domain.PortalSession;
import org.springframework.stereotype.Component;

@Component
public class PortalSessionMapper {

    public PortalSession mapToDomainEntity(PortalSessionRedisEntity portalSessionRedisEntity) {
        return new PortalSession(portalSessionRedisEntity.getSessionId());
    }
}
