package dev.checku.checkuserver.domain.push.application.service;

import dev.checku.checkuserver.domain.notification.adapter.out.persistence.NotificationJpaEntity;
import dev.checku.checkuserver.domain.notification.adapter.out.persistence.NotificationSpringDataRepository;
import dev.checku.checkuserver.domain.subject.application.service.SubjectService;
import dev.checku.checkuserver.domain.subject.adpater.out.persistence.SubjectJpaEntity;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
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
    private final NotificationSpringDataRepository notificationSpringDataRepository;
    private final FcmService fcmService;
    private final TopicService topicService;

    @Async
    @Transactional
    public void publish(String subjectNumber) {
        List<NotificationJpaEntity> notificationJpaEntityList = notificationSpringDataRepository.findAllBySubjectNumber(new SubjectNumber(subjectNumber));
        List<String> pushTokens = notificationJpaEntityList.stream()
                .map(notification -> notification.getUserJpaEntity().getPushToken())
                .collect(Collectors.toList());

        SubjectJpaEntity subjectJpaEntity = subjectService.getBy(new SubjectNumber(subjectNumber));
        fcmService.sendMessageToSubscriber(subjectNumber, "체쿠", String.format("%s ( %s ) 빈 자리가 있습니다.", subjectJpaEntity.getSubjectName(), subjectJpaEntity.getSubjectNumber()), pushTokens);

        topicService.deleteBy(new SubjectNumber(subjectNumber));
        notificationSpringDataRepository.deleteAllInBatch(notificationJpaEntityList);
    }
}
