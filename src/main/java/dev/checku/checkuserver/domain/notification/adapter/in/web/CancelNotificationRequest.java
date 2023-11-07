package dev.checku.checkuserver.domain.notification.adapter.in.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelNotificationRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;
}
