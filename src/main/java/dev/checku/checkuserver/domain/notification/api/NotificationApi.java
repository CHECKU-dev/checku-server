package dev.checku.checkuserver.domain.notification.api;

import dev.checku.checkuserver.domain.notification.application.NotificationService;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationApplyDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelDto;
import dev.checku.checkuserver.domain.notification.dto.SendMessageDto;
import dev.checku.checkuserver.global.advice.Login;
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
    public ResponseEntity<SendMessageDto.Response> sendMessageByTopic(@RequestBody SendMessageDto.Request request) {

        log.info("메세지 전송(Topic: {})", request.getTopic());
        notificationService.sendMessageByTopic(request);
        return ResponseEntity.ok(SendMessageDto.Response.of());
    }

    @Login
    @PostMapping
    public ResponseEntity<NotificationApplyDto.Response> applyNotification(
            @RequestBody NotificationApplyDto.Request dto,
            HttpServletRequest request

    ) {

        String session = request.getAttribute("session").toString();

        NotificationApplyDto.Response response = notificationService.applyNotification(dto, session);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<NotificationCancelDto.Response> cancelNotification(@RequestBody NotificationCancelDto.Request request) {

        NotificationCancelDto.Response response = notificationService.cancelNotification(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetNotificationDto.Response>> getNotification(@RequestBody GetNotificationDto.Request request) {

        List<GetNotificationDto.Response> response = notificationService.getNotification(request);
        return ResponseEntity.ok(response);
    }


}
