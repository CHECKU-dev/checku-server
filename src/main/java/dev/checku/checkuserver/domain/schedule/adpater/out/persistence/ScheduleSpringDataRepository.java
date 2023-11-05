package dev.checku.checkuserver.domain.schedule.adpater.out.persistence;

import dev.checku.checkuserver.domain.schedule.adpater.out.persistence.ScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleSpringDataRepository extends JpaRepository<ScheduleJpaEntity, Long> {
}
