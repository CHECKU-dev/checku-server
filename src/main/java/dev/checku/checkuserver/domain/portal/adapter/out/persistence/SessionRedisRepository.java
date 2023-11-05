package dev.checku.checkuserver.domain.portal.adapter.out.persistence;

import dev.checku.checkuserver.domain.portal.adapter.out.persistence.PortalSessionRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface SessionRedisRepository extends CrudRepository<PortalSessionRedisEntity, String> {
}
