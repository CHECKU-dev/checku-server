package dev.checku.checkuserver.domain.schedule.adpater.in.web;

import dev.checku.checkuserver.domain.schedule.adpater.out.persistence.ScheduleJpaEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final Long scheduleId;

    private final String title;

    private final String date;

    private final String time;

    private final LocalDateTime deadline;

    @Builder
    private GetScheduleResponse(Long scheduleId, String title, String date, String time, LocalDateTime deadline) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.deadline = deadline;
    }

    public static GetScheduleResponse from(ScheduleJpaEntity scheduleJpaEntity) {
        return GetScheduleResponse.builder()
                .scheduleId(scheduleJpaEntity.getId())
                .date(scheduleJpaEntity.getDate())
                .time(scheduleJpaEntity.getTime())
                .deadline(scheduleJpaEntity.getDeadline())
                .title(scheduleJpaEntity.getTitle())
                .build();
    }
}
