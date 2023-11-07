package dev.checku.checkuserver.domain.notification.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.notification.application.port.in.DeleteNotificationUseCase;
import dev.checku.checkuserver.domain.notification.application.port.out.DeleteNotificationPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteNotificationService implements DeleteNotificationUseCase {

    private final DeleteNotificationPort deleteNotificationPort;

    @Override
    public void delete(Long userId, String subjectNumber) {
        deleteNotificationPort.delete(userId, subjectNumber);
    }
}
