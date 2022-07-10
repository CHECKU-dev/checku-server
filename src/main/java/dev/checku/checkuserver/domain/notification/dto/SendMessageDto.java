package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class SendMessageDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank
        private String topic;

    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private String message;

        public static Response of(String message) {
            return Response.builder()
                    .message(message)
                    .build();

        }

    }

}
