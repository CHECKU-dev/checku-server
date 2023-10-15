package dev.checku.checkuserver.domain.notification.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationRequest {

    @NotNull
    private Long userId;

    public GetNotificationRequest(Long userId) {
        this.userId = userId;
    }
}
