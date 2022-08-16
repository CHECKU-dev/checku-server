package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.subject.application.MySubjectService;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationApplyDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationCancelDto;
import dev.checku.checkuserver.domain.notification.dto.SendMessageDto;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.exception.AlreadyAppliedNotificationException;
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
    public NotificationApplyDto.Response applyNotification(NotificationApplyDto.Request request, String session) {

        mySubjectService.checkValidSubject(request.getSubjectNumber(), session);

        User user = userService.getUser(request.getUserId());
        Notification notification = request.toEntity();

        if (notificationRepository.existsBySubjectNumberAndUser(notification.getSubjectNumber(), user)) {
            throw new AlreadyAppliedNotificationException(ErrorCode.ALREADY_APPLIED_NOTIFICATION);
        }

        if (!topicService.existsTopic(notification.getSubjectNumber())) {
            Topic topic = Topic.createTopic(notification.getSubjectNumber());
            topicService.saveTopic(topic);
        }

        Notification saveNotification = Notification.createNotification(notification, user);

        saveNotification = notificationRepository.save(saveNotification);
        fcmService.subscribeToTopic(user.getFcmToken(), saveNotification.getSubjectNumber());

        return NotificationApplyDto.Response.of(saveNotification);
    }

    @Transactional
    public NotificationCancelDto.Response cancelNotification(NotificationCancelDto.Request request) {
        String subjectNumber = request.getSubjectNumber();
        User user = userService.getUser(request.getUserId());

        Notification notification = notificationRepository.findBySubjectNumberAndUser(subjectNumber, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        fcmService.unsubscribeToTopic(user.getFcmToken(), subjectNumber);
        notificationRepository.delete(notification);

        if (!notificationRepository.existsBySubjectNumber(subjectNumber)) {
            topicService.deleteTopic(subjectNumber);
        }

        return NotificationCancelDto.Response.of(subjectNumber);

    }

    public List<GetNotificationDto.Response> getNotification(GetNotificationDto.Request request) {

        User user = userService.getUser(request.getUserId());

        List<Notification> notificationList = notificationRepository.findAllByUser(user);

        return notificationList.stream().map(GetNotificationDto.Response::of).collect(Collectors.toList());

    }

    @Async
    @Transactional
    public void sendMessageByTopic(SendMessageDto.Request request) {

        Subject subject = subjectService.getSubjectBySubjectNumber(request.getTopic());

        // topic(=subjectNumber)를 기준으로 notifcation 조회
        List<Notification> notificationList = notificationRepository.findAllBySubjectNumber(request.getTopic());

        List<String> tokens = notificationList.stream()
                .map(notification -> notification.getUser().getFcmToken()).collect(Collectors.toList());

        fcmService.sendTopicMessage(request.getTopic(), "체쿠", subject.getSubjectName() + "(" + subject.getSubjectNumber() + ")" + " 빈 자리가 있습니다.", tokens);

        // notification 삭제
        notificationRepository.deleteAllInBatch(notificationList);

        // topic 삭제
        topicService.deleteTopic(request.getTopic());

    }

    public void testNotification(String token) {
        fcmService.sendMessageTo(token, "체쿠", "대학영어1(1225) 빈 자리가 있습니다");

    }
}
