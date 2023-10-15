package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNotificationResponse {

    private String subjectNumber;

    private String subjectName;

    private String professor;

    @Builder
    private GetNotificationResponse(String subjectNumber, String subjectName, String professor) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.professor = professor;
    }

    public static GetNotificationResponse from(Notification notification) {
        return GetNotificationResponse.builder()
                .subjectNumber(notification.getSubjectNumber().getValue())
                .subjectName(notification.getSubjectName())
                .professor(notification.getProfessor())
                .build();

    }
}
