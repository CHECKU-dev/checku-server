package dev.checku.checkuserver.domain.schedule.dto;

import dev.checku.checkuserver.domain.schedule.entity.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ScheduleSearchDto {

    @Getter
    @Setter
    public static class Response {
        private Long scheduleId;

        private String title;

        private String date;

        private String time;

        private LocalDateTime deadline;

        @Builder
        public Response(Long scheduleId, String title, String date, String time, LocalDateTime deadline) {
            this.scheduleId = scheduleId;
            this.title = title;
            this.date = date;
            this.time = time;
            this.deadline = deadline;
        }

        public static ScheduleSearchDto.Response from(Schedule schedule) {
            return ScheduleSearchDto.Response.builder()
                    .scheduleId(schedule.getScheduleId())
                    .date(schedule.getDate())
                    .time(schedule.getTime())
                    .deadline(schedule.getDeadline())
                    .title(schedule.getTitle())
                    .build();

        }
    }

}
