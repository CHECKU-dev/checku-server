package dev.checku.checkuserver.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class NotificationCancelReq {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;

}
