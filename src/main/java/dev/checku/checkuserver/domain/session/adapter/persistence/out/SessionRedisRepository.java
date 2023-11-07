package dev.checku.checkuserver.domain.session.adapter.persistence.out;

import org.springframework.data.repository.CrudRepository;

public interface SessionRedisRepository extends CrudRepository<PortalSessionRedisEntity, String> {
}
