package dev.checku.checkuserver.domain.notification.api;

import dev.checku.checkuserver.domain.notification.application.NotificationService;
import dev.checku.checkuserver.domain.notification.dto.CancelNotificationRequest;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationResponse;
import dev.checku.checkuserver.domain.notification.dto.RegisterNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationApi {

    private final NotificationService notificationService;

    @PostMapping
    public void register(
            @Valid @RequestBody RegisterNotificationRequest request
    ) {
        notificationService.register(request);
    }

    @DeleteMapping
    public void cancelNotification(
            @Valid CancelNotificationRequest request
    ) {
        notificationService.cancel(request);
    }

    @GetMapping
    public ResponseEntity<List<GetNotificationResponse>> getNotifications(
            @RequestParam("userId") Long userId
    ) {
        List<GetNotificationResponse> response = notificationService.getAllBy(userId);
        return ResponseEntity.ok(response);
    }
}
