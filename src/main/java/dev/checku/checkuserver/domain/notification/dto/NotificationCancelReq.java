package dev.checku.checkuserver.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationCancelReq {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;

    @Builder
    public NotificationCancelReq(Long userId, String subjectNumber) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
    }
}
