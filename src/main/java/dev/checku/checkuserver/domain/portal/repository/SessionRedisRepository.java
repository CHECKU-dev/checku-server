package dev.checku.checkuserver.domain.portal.repository;

import dev.checku.checkuserver.domain.portal.domain.PortalSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionRedisRepository extends CrudRepository<PortalSession, String> {
}
