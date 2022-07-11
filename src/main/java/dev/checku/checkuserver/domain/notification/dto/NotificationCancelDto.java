package dev.checku.checkuserver.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class NotificationCancelDto {

    @Getter
    @Setter
    @ToString
    public static class Request {

        @NotBlank
        private Long userId;

        @NotBlank
        private String subjectNumber;

    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private String message;

        public static Response of(String subjectNumber) {
            return Response.builder()
                    .message(subjectNumber + " 알림 취소되었습니다.")
                    .build();

        }

    }


}
