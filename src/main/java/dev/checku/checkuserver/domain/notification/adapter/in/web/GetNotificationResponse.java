package dev.checku.checkuserver.domain.notification.adapter.in.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationResponse {

    private String subjectNumber;

    private String subjectName;

    private String professor;
}
