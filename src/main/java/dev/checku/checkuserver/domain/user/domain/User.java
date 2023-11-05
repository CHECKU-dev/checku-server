package dev.checku.checkuserver.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;

    private final String pushToken;

    @Builder
    public User(Long id, String pushToken) {
        this.id = id;
        this.pushToken = pushToken;
    }
}
