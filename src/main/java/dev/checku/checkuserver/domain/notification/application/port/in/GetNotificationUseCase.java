package dev.checku.checkuserver.domain.notification.application.port.in;

import dev.checku.checkuserver.domain.notification.domain.Notification;

import java.util.List;

public interface GetNotificationUseCase {

    List<Notification> getAllByUserId(Long userId);
}
