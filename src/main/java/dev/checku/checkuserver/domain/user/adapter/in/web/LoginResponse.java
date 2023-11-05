package dev.checku.checkuserver.domain.user.adapter.in.web;

import dev.checku.checkuserver.domain.user.domain.User;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final Long userId;

    private final String pushToken;

    public LoginResponse(User user) {
        this.userId = user.getId();
        this.pushToken = user.getPushToken();
    }
}
