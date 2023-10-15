package dev.checku.checkuserver.domain.user.dto;

import dev.checku.checkuserver.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotBlank
    private String pushToken;

    public User toEntity() {
        return new User(pushToken);
    }
}
