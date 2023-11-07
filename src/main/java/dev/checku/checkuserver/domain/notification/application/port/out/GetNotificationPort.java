package dev.checku.checkuserver.domain.notification.application.port.out;

import dev.checku.checkuserver.domain.notification.domain.Notification;

import java.util.List;

public interface GetNotificationPort {

    List<Notification> getAllByUserId(Long userId);
}
