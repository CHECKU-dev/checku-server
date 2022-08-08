package dev.checku.checkuserver.domain.schedule.repository;

import dev.checku.checkuserver.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
