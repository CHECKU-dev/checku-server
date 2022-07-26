package dev.checku.checkuserver.domain.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetScheduleResponse {

    private Long scheduleId;

    private String title;

    private String date;

    private String time;

    private LocalDateTime deadline;


    @Builder
    public GetScheduleResponse(Long scheduleId, String title, String date, String time, LocalDateTime deadline) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.deadline = deadline;
    }

    public static GetScheduleResponse from(Schedule schedule) {

        return GetScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .deadline(schedule.getDeadline())
                .title(schedule.getTitle())
                .build();

    }
}
