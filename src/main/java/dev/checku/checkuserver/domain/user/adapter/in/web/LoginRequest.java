package dev.checku.checkuserver.domain.user.adapter.in.web;

import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String pushToken;

    public UserJpaEntity toEntity() {
        return new UserJpaEntity(pushToken);
    }
}
