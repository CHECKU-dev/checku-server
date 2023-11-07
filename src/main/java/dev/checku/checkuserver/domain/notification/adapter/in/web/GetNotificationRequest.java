package dev.checku.checkuserver.domain.notification.adapter.in.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationRequest {

    @NotNull
    private Long userId;
}
