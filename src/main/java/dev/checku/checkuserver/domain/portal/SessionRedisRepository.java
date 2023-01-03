package dev.checku.checkuserver.domain.portal;

import org.springframework.data.repository.CrudRepository;

public interface SessionRedisRepository extends CrudRepository<PortalSession, String> {
}
