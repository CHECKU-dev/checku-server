package dev.checku.checkuserver.domain.push.service;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.infra.push.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PushService {

    private final SubjectService subjectService;
    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final TopicService topicService;

    @Async
    @Transactional
    public void publish(String subjectNumber) {
        List<Notification> notificationList = notificationRepository.findAllBySubjectNumber(new SubjectNumber(subjectNumber));
        List<String> pushTokens = notificationList.stream()
                .map(notification -> notification.getUser().getPushToken())
                .collect(Collectors.toList());

        Subject subject = subjectService.getBy(new SubjectNumber(subjectNumber));
        fcmService.sendMessageToSubscriber(subjectNumber, "체쿠", String.format("%s ( %s ) 빈 자리가 있습니다.", subject.getSubjectName(), subject.getSubjectNumber()), pushTokens);

        topicService.deleteBy(new SubjectNumber(subjectNumber));
        notificationRepository.deleteAllInBatch(notificationList);
    }
}
