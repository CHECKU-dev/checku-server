package dev.checku.checkuserver.domain.session.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PortalSessionRedisRepository extends CrudRepository<PortalSessionRedisEntity, String> {
}
