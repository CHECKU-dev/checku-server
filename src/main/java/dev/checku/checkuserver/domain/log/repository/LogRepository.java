package dev.checku.checkuserver.domain.log.repository;

import dev.checku.checkuserver.domain.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> , LogRepositoryCustom {
}
