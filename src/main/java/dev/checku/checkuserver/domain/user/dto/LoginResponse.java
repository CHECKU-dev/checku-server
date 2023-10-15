package dev.checku.checkuserver.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginResponse {

    private Long userId;

    private String pushToken;

    public LoginResponse(Long userId, String pushToken) {
        this.userId = userId;
        this.pushToken = pushToken;
    }
}
