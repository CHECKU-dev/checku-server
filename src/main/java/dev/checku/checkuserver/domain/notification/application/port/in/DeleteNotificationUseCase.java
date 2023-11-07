package dev.checku.checkuserver.domain.notification.application.port.in;

public interface DeleteNotificationUseCase {

    void delete(Long userId, String subjectNumber);
}
