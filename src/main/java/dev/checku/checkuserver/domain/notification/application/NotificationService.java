package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.notification.dao.NotificationRepository;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationApplyDto;
import dev.checku.checkuserver.domain.notification.dto.SendMessageDto;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationService {

    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Transactional
    public NotificationApplyDto.Response createNotification(NotificationApplyDto.Request request) {

        User user = userService.getUser(request.getUserId());

        Notification notification = request.toEntity();
        Notification saveNotification = Notification.createNotification(notification, user);

        saveNotification = notificationRepository.save(saveNotification);
        fcmService.subscribeToTopic(user.getFcmToken(), saveNotification.getSubjectNumber());

        return NotificationApplyDto.Response.of(saveNotification);
    }

    public List<GetNotificationDto.Response> getNotification(GetNotificationDto.Request request) {

        User user = userService.getUser(request.getUserId());

        List<Notification> notificationList = notificationRepository.findAllByUser(user);

        return notificationList.stream().map(notification ->
                GetNotificationDto.Response.of(notification)).collect(Collectors.toList());

    }


    public void sendMessageByTopic(SendMessageDto.Request request) {

        // topic(=subjectNumber)를 기준으로 notifcation 조회
        List<Notification> notificationList = notificationRepository.findAllBySubjectNumber(request.getTopic());

        List<String> tokens = notificationList.stream()
                .map(notification -> notification.getUser().getFcmToken()).collect(Collectors.toList());

        //TODO 변경
        fcmService.sendTopicMessage(request.getTopic(), "TEST", "TEST", tokens);

        // notificiation 삭제
        notificationRepository.deleteAllInBatch(notificationList);

    }
}
