package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class SendMessageDto {

    @Getter
    @Setter
    @ToString
    public static class Request {

        @NotBlank
        private String topic;

    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private String message;

        public static Response of() {
            return Response.builder()
                    .message("메세지 전송에 성공하였습니다.")
                    .build();

        }

    }

}
