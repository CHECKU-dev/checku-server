package dev.checku.checkuserver.domain.notification.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.notification.application.port.in.CreateNotificationUseCase;
import dev.checku.checkuserver.domain.notification.application.port.out.CreateNotificationPort;
import dev.checku.checkuserver.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateNotificationService implements CreateNotificationUseCase {

    private final CreateNotificationPort createNotificationPort;

    @Override
    public Notification create(Long userId, String subjectNumber) {
        return createNotificationPort.create(userId, subjectNumber);
    }
}
