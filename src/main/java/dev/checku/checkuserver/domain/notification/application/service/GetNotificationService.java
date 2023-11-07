package dev.checku.checkuserver.domain.notification.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.notification.application.port.in.GetNotificationUseCase;
import dev.checku.checkuserver.domain.notification.application.port.out.GetNotificationPort;
import dev.checku.checkuserver.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetNotificationService implements GetNotificationUseCase {

    private final GetNotificationPort getNotificationPort;

    @Override
    public List<Notification> getAllByUserId(Long userId) {
        return getNotificationPort.getAllByUserId(userId);
    }
}
