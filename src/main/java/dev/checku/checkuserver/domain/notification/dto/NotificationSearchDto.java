package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class NotificationSearchDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        @NotNull
        private Long userId;

        @Builder
        public Request(Long userId) {
            this.userId = userId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private String subjectNumber;

        private String subjectName;

        private String professor;

        @Builder
        public Response(String subjectNumber, String subjectName, String professor) {
            this.subjectNumber = subjectNumber;
            this.subjectName = subjectName;
            this.professor = professor;
        }

        public static Response of(Notification notification) {
            return Response.builder()
                    .subjectNumber(notification.getSubjectNumber())
                    .subjectName(notification.getSubjectName())
                    .professor(notification.getProfessor())
                    .build();

        }
    }

}
