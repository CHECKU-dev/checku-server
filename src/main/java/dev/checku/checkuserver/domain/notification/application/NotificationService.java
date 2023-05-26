package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendReq;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final SubjectService subjectService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final TopicService topicService;

    @Transactional
    public boolean applyNotification(NotificationRegisterReq request) {
        subjectService.checkValidSubject(request.getSubjectNumber());

        User user = userService.getUserById(request.getUserId());
        Notification notification = request.toEntity(user);

        if (notificationRepository.existsBySubjectNumberAndUserId(notification.getSubjectNumber(), user.getId())) {
            throw new NotificationAlreadyRegisteredException(ErrorCode.NOTIFICATION_ALREADY_REGISTERED);
        }

        // topic 존재하지 않으면 topic 생성 -> 메서드 분리
        if (!topicService.existsBySubjectNumber(notification.getSubjectNumber())) {
            Topic topic = Topic.createTopic(notification.getSubjectNumber());
            topicService.saveTopic(topic);
        }

        Notification saveNotification = notificationRepository.save(notification);
        fcmService.subscribeToTopic(user.getFcmToken(), saveNotification.getSubjectNumber());

        return true;
    }

    @Transactional
    public void cancelNotification(NotificationCancelReq request) {
        String subjectNumber = request.getSubjectNumber();
        User user = userService.getUserById(request.getUserId());

        Notification notification = notificationRepository.findBySubjectNumberAndUserId(subjectNumber, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        fcmService.unsubscribeToTopic(user.getFcmToken(), subjectNumber);
        notificationRepository.delete(notification);

        // 더이상 notification 존재하지 않으면 topic 삭제
        if (!notificationRepository.existsBySubjectNumber(subjectNumber)) {
            topicService.deleteTopicBySubjectNumber(subjectNumber);
        }
    }

    public List<NotificationSearchDto.Response> getNotificationsByUserId(NotificationSearchDto.Request request) {
        List<Notification> notificationList = notificationRepository.findAllByUserId(request.getUserId());

        return notificationList.stream()
                .map(NotificationSearchDto.Response::of)
                .collect(Collectors.toList());
    }

    //    @Async
    @Transactional
    public void sendMessageByTopic(NotificationSendReq request) {
        String subjectNumber = request.getTopic();

        Subject subject = subjectService.getSubjectBySubjectNumber(subjectNumber);

        // topic(subjectNumber)을 구독 중인 유저에게 푸시 알림 제공
        List<Notification> notificationList = notificationRepository.findAllBySubjectNumber(subjectNumber);
        List<String> fcmTokens = notificationList.stream()
                .map(notification -> notification.getUser().getFcmToken())
                .collect(Collectors.toList());

        fcmService.sendMessageToSubscriber(subjectNumber, "체쿠", String.format("%s ( %s ) 빈 자리가 있습니다.", subject.getSubjectName(), subject.getSubjectNumber()), fcmTokens);

        // notification 삭제
        notificationRepository.deleteAllInBatch(notificationList);
        // topic 삭제
        topicService.deleteTopicBySubjectNumber(subjectNumber);

    }

    public void sendTestMessageByToken(String fcmToken) {
        fcmService.sendMessageTo(fcmToken, "체쿠", "test");
    }

}
