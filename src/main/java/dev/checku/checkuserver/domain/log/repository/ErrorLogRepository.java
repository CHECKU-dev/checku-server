package dev.checku.checkuserver.domain.log.repository;

import dev.checku.checkuserver.domain.log.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
