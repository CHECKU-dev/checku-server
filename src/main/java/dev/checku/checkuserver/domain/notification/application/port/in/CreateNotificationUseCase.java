package dev.checku.checkuserver.domain.notification.application.port.in;

import dev.checku.checkuserver.domain.notification.domain.Notification;

public interface CreateNotificationUseCase {

    Notification create(Long userId, String subjectNumber);
}
