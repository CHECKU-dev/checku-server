package dev.checku.checkuserver.domain.user.dto;

import dev.checku.checkuserver.domain.topic.Topic;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserLoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotEmpty
        private String fcmToken;

        public User toEntity() {
            return User.builder()
                    .fcmToken(fcmToken)
                    .build();
        }

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long userId;

        private String fcmToken;

        public static Response of(User user) {

            return Response.builder()
                    .userId(user.getId())
                    .fcmToken(user.getFcmToken())
                    .build();
        }

    }

}
