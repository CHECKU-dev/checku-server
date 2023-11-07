package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.notification.application.port.out.CreateNotificationPort;
import dev.checku.checkuserver.domain.notification.application.port.out.DeleteNotificationPort;
import dev.checku.checkuserver.domain.notification.application.port.out.GetNotificationPort;
import dev.checku.checkuserver.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements CreateNotificationPort, DeleteNotificationPort, GetNotificationPort {

    private final NotificationSpringDataRepository notificationSpringDataRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification create(Long userId, String subjectNumber) {
        NotificationJpaEntity notificationJpaEntity = notificationSpringDataRepository.save(new NotificationJpaEntity(userId, subjectNumber));
        return notificationMapper.mapToDomainEntity(notificationJpaEntity);
    }

    @Override
    public void delete(Long userId, String subjectNumber) {
        notificationSpringDataRepository.deleteByUserIdAndSubjectNumber(userId, subjectNumber);
    }

    @Override
    public List<Notification> getAllByUserId(Long userId) {
        List<NotificationJpaEntity> notificationJpaEntities = notificationSpringDataRepository.findAllByUserId(userId);
        return notificationJpaEntities.stream()
                .map(notificationMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
