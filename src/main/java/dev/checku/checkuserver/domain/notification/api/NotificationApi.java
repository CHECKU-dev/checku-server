package dev.checku.checkuserver.domain.notification.api;

import dev.checku.checkuserver.domain.notification.application.NotificationService;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification") // notification -> notifications
public class NotificationApi {

    private final NotificationService notificationService;

    @PostMapping("/topic")
    public void sendNotification(@RequestBody NotificationSendReq request) {
        notificationService.sendMessageByTopic(request);
    }

    @PostMapping
    public void registerNotification(@RequestBody @Valid NotificationRegisterReq request) {
        notificationService.applyNotification(request);
    }

    @DeleteMapping
    public void cancelNotification(@RequestBody @Valid NotificationCancelReq request) {
        notificationService.cancelNotification(request);
    }

    @GetMapping
    public ResponseEntity<List<NotificationSearchDto.Response>> getNotifications(
            @Valid NotificationSearchDto.Request request
    ) {
        List<NotificationSearchDto.Response> response = notificationService.getNotifications(request);
        return ResponseEntity.ok(response);
    }


    // 테스팅용 API
    @GetMapping("/test/{fcmToken}")
    public ResponseEntity<Void> testNotification(
            @PathVariable("fcmToken") String fcmToken
    ) {
        notificationService.sendTestMessageByToken(fcmToken);
        return ResponseEntity.ok().build();
    }

}
