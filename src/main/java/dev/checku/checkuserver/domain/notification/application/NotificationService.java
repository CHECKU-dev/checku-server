package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.subject.application.MySubjectService;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendDto;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.exception.NotificationAlreadyRegisteredException;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.topic.entity.Topic;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final MySubjectService mySubjectService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final TopicService topicService;

    @Transactional
    public NotificationRegisterDto.Response applyNotification(NotificationRegisterDto.Request request) {
        subjectService.checkValidSubject(request.getSubjectNumber());

        User user = userService.getUserById(request.getUserId());
        Notification notification = request.toEntity();

        if (notificationRepository.existsBySubjectNumberAndUser(notification.getSubjectNumber(), user)) {
            throw new NotificationAlreadyRegisteredException(ErrorCode.NOTIFICATION_ALREADY_REGISTERED);
        }

        if (!topicService.existsBySubjectNumber(notification.getSubjectNumber())) {
            Topic topic = Topic.createTopic(notification.getSubjectNumber());
            topicService.saveTopic(topic);
        }

        Notification saveNotification = Notification.createNotification(notification, user);

        saveNotification = notificationRepository.save(saveNotification);
        fcmService.subscribeToTopic(user.getFcmToken(), saveNotification.getSubjectNumber());

        return NotificationRegisterDto.Response.of(saveNotification);
    }

    @Transactional
    public NotificationCancelDto.Response cancelNotification(NotificationCancelDto.Request request) {
        String subjectNumber = request.getSubjectNumber();
        User user = userService.getUserById(request.getUserId());

        Notification notification = notificationRepository.findBySubjectNumberAndUser(subjectNumber, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        fcmService.unsubscribeToTopic(user.getFcmToken(), subjectNumber);
        notificationRepository.delete(notification);

        if (!notificationRepository.existsBySubjectNumber(subjectNumber)) {
            topicService.deleteTopicBySubjectNumber(subjectNumber);
        }

        return NotificationCancelDto.Response.of(subjectNumber);
    }

    public List<NotificationSearchDto.Response> getNotification(NotificationSearchDto.Request request) {
        User user = userService.getUserById(request.getUserId());
        List<Notification> notificationList = notificationRepository.findAllByUser(user);

        return notificationList.stream()
                .map(NotificationSearchDto.Response::of)
                .collect(Collectors.toList());
    }

    @Async
    @Transactional
    public void sendMessageByTopic(NotificationSendDto.Request request) {
        String subjectNumber = request.getTopic();

        Subject subject = subjectService.getSubjectBySubjectNumber(subjectNumber);

        // topic(subjectNumber)을 구독 중인 유저에게 푸시 알림 제공
        List<Notification> notificationList = notificationRepository.findAllBySubjectNumber(subjectNumber);
        List<String> fcmTokens = notificationList.stream()
                .map(notification -> notification.getUser().getFcmToken())
                .collect(Collectors.toList());
        fcmService.sendMessageToSubscriber(subjectNumber, "체쿠", subject.getSubjectName() + "(" + subject.getSubjectNumber() + ")" + " 빈 자리가 있습니다.", fcmTokens);

        // notification 삭제
        notificationRepository.deleteAllInBatch(notificationList);
        // topic 삭제
        topicService.deleteTopicBySubjectNumber(subjectNumber);
    }

    public void sendTestMessageByToken(String fcmToken) {
        fcmService.sendMessageTo(fcmToken, "체쿠", "test");
    }

}
