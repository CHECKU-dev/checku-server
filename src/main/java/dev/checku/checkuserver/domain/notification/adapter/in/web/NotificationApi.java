package dev.checku.checkuserver.domain.notification.adapter.in.web;

import dev.checku.checkuserver.domain.notification.application.port.in.CreateNotificationUseCase;
import dev.checku.checkuserver.domain.notification.application.port.in.DeleteNotificationUseCase;
import dev.checku.checkuserver.domain.notification.application.port.in.GetNotificationUseCase;
import dev.checku.checkuserver.domain.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationApi {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;
    private final GetNotificationUseCase getNotificationUseCase;

    @PostMapping
    public void create(
            @Valid @RequestBody CreateNotificationRequest request
    ) {
        createNotificationUseCase.create(request.getUserId(), request.getSubjectNumber());
    }

    @DeleteMapping
    public void cancelNotification(
            @Valid CancelNotificationRequest request
    ) {
        deleteNotificationUseCase.delete(request.getUserId(), request.getSubjectNumber());
    }

    @GetMapping
    public ResponseEntity<List<GetNotificationResponse>> getNotifications(
            @RequestParam("userId") Long userId
    ) {
        List<Notification> notifications = getNotificationUseCase.getAllByUserId(userId);
        return ResponseEntity.ok(null); // TODO
    }
}
