package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class NotificationApplyDto {

    @Getter
    @Setter
    @ToString
    public static class Request {

        @NotBlank
        private Long userId;

        @NotBlank
        private String subjectNumber;

        @NotBlank
        private String subjectName;

        public Notification toEntity() {
            return Notification.builder()
                    .subjectNumber(subjectNumber)
                    .subjectName(subjectName)
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
