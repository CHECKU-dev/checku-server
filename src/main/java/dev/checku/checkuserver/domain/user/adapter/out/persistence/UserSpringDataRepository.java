package dev.checku.checkuserver.domain.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringDataRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByPushToken(String pushToken);
}
