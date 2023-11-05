package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.notification.dto.CancelNotificationRequest;
import dev.checku.checkuserver.domain.notification.dto.GetNotificationResponse;
import dev.checku.checkuserver.domain.notification.dto.RegisterNotificationRequest;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.exception.AlreadyRegisteredNotificationException;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import dev.checku.checkuserver.domain.user.application.port.out.LoadUserPort;
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
    private final LoadUserPort loadUserPort;
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final TopicService topicService;

    @Transactional
    public boolean register(RegisterNotificationRequest request) {
        subjectService.validateSubjectHasVacancy(request.getSubjectNumber());
//
//        UserJpaEntity userJpaEntity = userService.getBy(request.getUserId());
//        Notification newNotification = request.toEntity(userJpaEntity);
//
//        validateDuplicateRegistration(userJpaEntity, newNotification.getSubjectNumber());
//
//        Notification savedNotification = notificationRepository.save(newNotification);
//
//        createTopicIfNotExists(newNotification);
//        fcmService.subscribeToTopic(userJpaEntity.getPushToken(), savedNotification.getSubjectNumber().getValue());

        return true;
    }

    private void validateDuplicateRegistration(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        if (notificationRepository.existsByUserJpaEntityAndSubjectNumber(userJpaEntity, subjectNumber)) {
            throw new AlreadyRegisteredNotificationException();
        }
    }

    private void createTopicIfNotExists(Notification notification) {
        if (!topicService.existsBy(notification.getSubjectNumber())) {
//            TopicJpaEntity topicJpaEntity = TopicJpaEntity.create((notification.getSubjectNumber()));
//            topicService.save(topicJpaEntity);
        }
    }

    @Transactional
    public void cancel(CancelNotificationRequest request) {
//        UserJpaEntity userJpaEntity = userService.getBy(request.getUserId());
//
//        Notification notification = getNotificationBy(userJpaEntity, request.getSubjectNumber());
//        notificationRepository.delete(notification);
//
//        fcmService.unsubscribeToTopic(userJpaEntity.getPushToken(), notification.getSubjectNumber());
//
//        deleteTopicIfLastNotification(notification);
    }

    public List<GetNotificationResponse> getAllBy(Long userId) {
        List<Notification> notificationList = notificationRepository.findAllByUserJpaEntityId(userId);

        return notificationList.stream()
                .map(GetNotificationResponse::from)
                .collect(Collectors.toList());
    }

    private Notification getNotificationBy(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        return notificationRepository.findByUserJpaEntityAndSubjectNumber(userJpaEntity, subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    private void deleteTopicIfLastNotification(Notification notification) {
        if (!notificationRepository.existsBySubjectNumber(notification.getSubjectNumber())) {
            topicService.deleteBy(notification.getSubjectNumber());
        }
    }
}
