package dev.checku.checkuserver.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class NotificationSendReq {
    @NotBlank
    private String topic;

    @Builder
    public NotificationSendReq(String topic) {
        this.topic = topic;
    }
}
