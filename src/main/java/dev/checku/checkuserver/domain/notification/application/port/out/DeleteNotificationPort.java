package dev.checku.checkuserver.domain.notification.application.port.out;

public interface DeleteNotificationPort {

    void delete(Long userId, String subjectNumber);
}
