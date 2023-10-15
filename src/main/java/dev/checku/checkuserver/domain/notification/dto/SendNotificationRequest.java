package dev.checku.checkuserver.domain.notification.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendNotificationRequest {

    @NotBlank
    private String topic;

    public SendNotificationRequest(String topic) {
        this.topic = topic;
    }
}
