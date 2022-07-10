package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class GetNotificationDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank
        private Long userId;

    }

    @Getter
    @Setter
    public static class Response {

        private String subjectNumber;

        private String subjectName;

        @Builder
        public Response( String subjectNumber, String subjectName) {
            this.subjectNumber = subjectNumber;
            this.subjectName = subjectName;
        }

        public static Response of(Notification notification) {

            return Response.builder()
                    .subjectNumber(notification.getSubjectNumber())
                    .subjectName(notification.getSubjectName())
                    .build();

        }
    }


}
