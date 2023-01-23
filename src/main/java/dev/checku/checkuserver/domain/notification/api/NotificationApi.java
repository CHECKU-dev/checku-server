package dev.checku.checkuserver.domain.notification.api;

import dev.checku.checkuserver.domain.notification.application.NotificationService;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationApi {

    private final NotificationService notificationService;

    @PostMapping("/topic")
    public ResponseEntity<NotificationSendDto.Response> notificationSend(@RequestBody NotificationSendDto.Request request) {
        notificationService.sendMessageByTopic(request);
        log.info("메세지 전송(Topic: {})", request.getTopic());
        return ResponseEntity.ok(NotificationSendDto.Response.of());
    }

    @PostMapping
    public ResponseEntity<NotificationRegisterDto.Response> notificationRegister(
            @RequestBody NotificationRegisterDto.Request dto
    ) {
        NotificationRegisterDto.Response response = notificationService.applyNotification(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<NotificationCancelDto.Response> notificationCancel(NotificationCancelDto.Request request) {
        NotificationCancelDto.Response response = notificationService.cancelNotification(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificationSearchDto.Response>> notificationSearch(NotificationSearchDto.Request request) {
        List<NotificationSearchDto.Response> response = notificationService.getNotification(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{fcmToken}")
    public ResponseEntity<Void> notificationTest(
            @PathVariable("fcmToken") String fcmToken
    ) {
        notificationService.sendTestMessageByToken(fcmToken);
        return ResponseEntity.ok().build();
    }

}
