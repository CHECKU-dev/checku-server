package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotificationRegisterDto {

    @Getter
    @Setter
    @ToString
    public static class Request {
        @NotNull
        private Long userId;

        @NotBlank
        private String subjectNumber;

        @NotBlank
        private String subjectName;

        @NotBlank
        private String professor;

        public Notification toEntity() {
            return Notification.builder()
                    .subjectNumber(subjectNumber)
                    .subjectName(subjectName)
                    .professor(professor)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long notificationId;

        public static Response of(Notification notification) {
            return Response.builder()
                    .notificationId(notification.getNotificationId())
                    .build();
        }
    }


}
