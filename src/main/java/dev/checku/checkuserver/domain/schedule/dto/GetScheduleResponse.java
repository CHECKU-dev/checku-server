package dev.checku.checkuserver.domain.schedule.dto;

import dev.checku.checkuserver.domain.schedule.entity.Schedule;
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

    public static GetScheduleResponse from(Schedule schedule) {
        return GetScheduleResponse.builder()
                .scheduleId(schedule.getId())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .deadline(schedule.getDeadline())
                .title(schedule.getTitle())
                .build();
    }
}
