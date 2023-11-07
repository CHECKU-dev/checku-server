package dev.checku.checkuserver.domain.notification.application.port.out;

import dev.checku.checkuserver.domain.notification.domain.Notification;

public interface CreateNotificationPort {

    Notification create(Long userId, String subjectNumber);
}
