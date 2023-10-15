package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.notification.dto.CancelNotificationRequest;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationResponse;
import dev.checku.checkuserver.domain.notification.dto.RegisterNotificationRequest;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.exception.AlreadyRegisteredNotificationException;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.topic.entity.Topic;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.infra.push.FcmService;
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
    public boolean register(RegisterNotificationRequest request) {
        subjectService.validateSubjectHasVacancy(request.getSubjectNumber());

        User user = userService.getBy(request.getUserId());
        Notification newNotification = request.toEntity(user);

        validateDuplicateRegistration(user, newNotification.getSubjectNumber());

        Notification savedNotification = notificationRepository.save(newNotification);

        createTopicIfNotExists(newNotification);
        fcmService.subscribeToTopic(user.getPushToken(), savedNotification.getSubjectNumber().getValue());

        return true;
    }

    private void validateDuplicateRegistration(User user, SubjectNumber subjectNumber) {
        if (notificationRepository.existsByUserAndSubjectNumber(user, subjectNumber)) {
            throw new AlreadyRegisteredNotificationException();
        }
    }

    private void createTopicIfNotExists(Notification notification) {
        if (!topicService.existsBy(notification.getSubjectNumber())) {
            Topic topic = Topic.create((notification.getSubjectNumber()));
            topicService.save(topic);
        }
    }

    @Transactional
    public void cancel(CancelNotificationRequest request) {
        User user = userService.getBy(request.getUserId());

        Notification notification = getNotificationBy(user, request.getSubjectNumber());
        notificationRepository.delete(notification);

        fcmService.unsubscribeToTopic(user.getPushToken(), notification.getSubjectNumber());

        deleteTopicIfLastNotification(notification);
    }

    public List<GetNotificationResponse> getAllBy(Long userId) {
        List<Notification> notificationList = notificationRepository.findAllByUserId(userId);

        return notificationList.stream()
                .map(GetNotificationResponse::from)
                .collect(Collectors.toList());
    }

    private Notification getNotificationBy(User user, SubjectNumber subjectNumber) {
        return notificationRepository.findByUserAndSubjectNumber(user, subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    private void deleteTopicIfLastNotification(Notification notification) {
        if (!notificationRepository.existsBySubjectNumber(notification.getSubjectNumber())) {
            topicService.deleteBy(notification.getSubjectNumber());
        }
    }
}
