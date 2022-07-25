package dev.checku.checkuserver.domain.schedule;

import lombok.Builder;
import java.time.LocalDateTime;

public class GetScheduleResponse {

    private Long scheduleId;

    private String title;

    private String date;

    private LocalDateTime deadline;


    @Builder
    public GetScheduleResponse(Long scheduleId, String title, String date, LocalDateTime deadline) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.date = date;
        this.deadline = deadline;
    }

    public static GetScheduleResponse from(Schedule schedule) {

        return GetScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .date(schedule.getDate())
                .deadline(schedule.getDeadline())
                .title(schedule.getTitle())
                .build();

    }
}
