package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.domain.notification.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification mapToDomainEntity(NotificationJpaEntity notificationJpaEntity) {
        return Notification.builder()
                .id(notificationJpaEntity.getId())
                .userId(notificationJpaEntity.getUserId())
                .subjectNumber(new SubjectNumber(notificationJpaEntity.getSubjectNumber()))
                .build();
    }
}
