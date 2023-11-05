package dev.checku.checkuserver.domain.notification.adapter.in.web;

import dev.checku.checkuserver.domain.notification.adapter.out.persistence.NotificationJpaEntity;
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

    public static GetNotificationResponse from(NotificationJpaEntity notificationJpaEntity) {
        return GetNotificationResponse.builder()
                .subjectNumber(notificationJpaEntity.getSubjectNumber().getValue())
                .subjectName(notificationJpaEntity.getSubjectName())
                .professor(notificationJpaEntity.getProfessor())
                .build();

    }
}
