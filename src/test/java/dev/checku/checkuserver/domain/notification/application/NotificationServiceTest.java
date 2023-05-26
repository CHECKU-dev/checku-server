package dev.checku.checkuserver.domain.notification.application;

import dev.checku.checkuserver.domain.notification.dto.NotificationCancelReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationRegisterReq;
import dev.checku.checkuserver.domain.notification.dto.NotificationSearchDto;
import dev.checku.checkuserver.domain.notification.dto.NotificationSendReq;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.notification.repository.NotificationRepository;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.subject.enums.SubjectType;
import dev.checku.checkuserver.domain.topic.application.TopicService;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.infra.notification.FcmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;
    @Mock
    private SubjectService subjectService;
    @Mock
    private UserService userService;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private FcmService fcmService;
    @Mock
    private TopicService topicService;


    @Test
    @DisplayName("알림을 신청한다.")
    void applyNotification() {
        // given
        User user = createUser(1L, "dummy");
        Notification notification = createNotification("0001", "모바일 프로그래밍", "professor1", user);

//        given(subjectService.checkValidSubject(anyString())).willReturn(true);
        given(userService.getUserById(anyLong())).willReturn(user);
        given(notificationRepository.existsBySubjectNumberAndUserId(anyString(), anyLong())).willReturn(false);
        given(topicService.existsBySubjectNumber(anyString())).willReturn(false);
        given(notificationRepository.save(any())).willReturn(notification);

        NotificationRegisterReq request = NotificationRegisterReq.builder()
                .userId(user.getId())
                .subjectNumber("0001")
                .subjectName("모바일 프로그래밍")
                .professor("professor1")
                .build();
        // when
        boolean result = notificationService.applyNotification(request);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유저가 신청했던 알림을 취소한다.")
    void cancelNotification() {
        // given
        String targetSubjectNumber = "Subject1";
        Long targetUserId = 1L;
        String fcmToken = "dummy";
        User user = createUser(targetUserId, fcmToken);
        Notification notification = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", user);

        given(userService.getUserById(targetUserId)).willReturn(user);
        given(notificationRepository.findBySubjectNumberAndUserId(targetSubjectNumber, targetUserId)).willReturn(Optional.of(notification));
        given(notificationRepository.existsBySubjectNumber(targetSubjectNumber)).willReturn(false);

        NotificationCancelReq request = NotificationCancelReq.builder()
                .userId(targetUserId)
                .subjectNumber(targetSubjectNumber)
                .build();

        // when
        notificationService.cancelNotification(request);

        // then
        verify(userService, times(1)).getUserById(targetUserId);
        verify(notificationRepository, times(1)).findBySubjectNumberAndUserId(targetSubjectNumber, targetUserId);
        verify(fcmService, times(1)).unsubscribeToTopic(fcmToken, targetSubjectNumber);
        verify(notificationRepository, times(1)).delete(notification);
        verify(notificationRepository, times(1)).existsBySubjectNumber(targetSubjectNumber);
        verify(topicService, times(1)).deleteTopicBySubjectNumber(targetSubjectNumber);
    }

    @Test
    @DisplayName("유저가 신청한 모든 알림을 가져온다.")
    void getNotificationsByUserId() {
        // given
        Long targetUserId = 1L;
        User user = createUser(targetUserId, "dummy");
        Notification notification1 = createNotification("0001", "모바일 프로그래밍", "professor1", user);
        Notification notification2 = createNotification("0002", "JAVA 프로그래밍", "professor2", user);
        List<Notification> notificationList = List.of(notification1, notification2);
        given(notificationRepository.findAllByUserId(targetUserId)).willReturn(notificationList);

        NotificationSearchDto.Request request = NotificationSearchDto.Request.builder()
                .userId(targetUserId)
                .build();
        // when
        List<NotificationSearchDto.Response> results = notificationService.getNotificationsByUserId(request);

        // then

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting("subjectNumber", "subjectName", "professor")
                .containsExactlyInAnyOrder(
                        tuple("0001", "모바일 프로그래밍", "professor1"),
                        tuple("0002", "JAVA 프로그래밍", "professor2")
                );
    }

    @Test
    @DisplayName("토픽에 해당하는 메시지를 전송한다.")
    void sendMessageByTopic() {
        // given
        String targetSubjectNumber = "0001";
        User user = createUser(1L, "dummy");

        Subject subject = Subject.createSubject(targetSubjectNumber, "dummy", SubjectType.MAJOR);

        Notification notification1 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", user);
        Notification notification2 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", user);
        List<Notification> notificationList = List.of(notification1, notification2);

        given(subjectService.getSubjectBySubjectNumber(targetSubjectNumber)).willReturn(subject);
        given(notificationRepository.findAllBySubjectNumber(anyString())).willReturn(notificationList);

        NotificationSendReq request = NotificationSendReq.builder()
                .topic(targetSubjectNumber)
                .build();

        // when
        notificationService.sendMessageByTopic(request);

        // then
        verify(subjectService, times(1)).getSubjectBySubjectNumber(targetSubjectNumber);
        verify(notificationRepository, times(1)).findAllBySubjectNumber(targetSubjectNumber);
        verify(fcmService, times(1)).sendMessageToSubscriber(anyString(), anyString(), anyString(), anyList());
        verify(notificationRepository, times(1)).deleteAllInBatch(notificationList);
        verify(topicService, times(1)).deleteTopicBySubjectNumber(targetSubjectNumber);
    }

    private static User createUser(Long userId, String fcmToken) {
        return User.builder()
                .id(userId)
                .fcmToken(fcmToken)
                .build();
    }

    private static Notification createNotification(String subjectNumber, String subjectName, String professor, User user) {
        return Notification.builder()
                .subjectNumber(subjectNumber)
                .subjectName(subjectName)
                .professor(professor)
                .user(user)
                .build();
    }

}