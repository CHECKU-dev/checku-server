package dev.checku.checkuserver.domain.notification.repository;

import dev.checku.checkuserver.IntegrationTestSupport;
import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NotificationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("유저가 신청한 알림을 조회한다.")
    void findAllByUser() {
        // given
        User user1 = createUser();
        User user2 = createUser();
        User targetUser = userRepository.save(user1);
        User tempUser = userRepository.save(user2);

        Notification notification1 = createNotification("0001", "모바일 프로그래밍", "professor1", targetUser);
        Notification notification2 = createNotification("0002", "차세대 분산 시스템", "professor2", targetUser);
        Notification notification3 = createNotification("0003", "JAVA 프로그래밍", "professor3", tempUser);
        notificationRepository.saveAll(List.of(notification1, notification2, notification3));

        // when
        List<Notification> notifications = notificationRepository.findAllByUserId(targetUser.getId());

        // then
        Assertions.assertThat(notifications).hasSize(2)
                .extracting("subjectNumber", "subjectName", "professor")
                .containsExactlyInAnyOrder(
                        tuple("0001", "모바일 프로그래밍", "professor1"),
                        tuple("0002", "차세대 분산 시스템", "professor2")
                );
    }

    @Test
    @DisplayName("과목번호를 가진 모든 알림을 조회햔다.")
    void findAllBySubjectNumber() {
        // given
        String targetSubjectNumber = "0001";
        User user1 = createUser();
        User user2 = createUser();
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        Notification notification1 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", savedUser1);
        Notification notification2 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", savedUser2);
        Notification notification3 = createNotification("0002", "JAVA 프로그래밍", "professor3", savedUser1);
        notificationRepository.saveAll(List.of(notification1, notification2, notification3));

        // when
        List<Notification> notifications = notificationRepository.findAllBySubjectNumber(targetSubjectNumber);

        // then
        Assertions.assertThat(notifications).hasSize(2)
                .extracting("subjectNumber", "subjectName", "professor")
                .containsExactlyInAnyOrder(
                        tuple("0001", "모바일 프로그래밍", "professor1"),
                        tuple("0001", "모바일 프로그래밍", "professor1")
                );
    }

    @Test
    @DisplayName("과목번호와 유저를 통해 알림을 조회한다. - 존재하는 경우")
    void findBySubjectNumberAndUser1() {
        // given
        String targetSubjectNumber = "0001";
        User user1 = createUser();
        User user2 = createUser();
        User targetUser = userRepository.save(user1);
        User tempUser = userRepository.save(user2);

        Notification notification1 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", targetUser);
        Notification notification2 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", tempUser);
        Notification notification3 = createNotification("0002", "JAVA 프로그래밍", "professor3", tempUser);
        notificationRepository.saveAll(List.of(notification1, notification2, notification3));

        // when
        Notification notification = notificationRepository.findBySubjectNumberAndUserId(targetSubjectNumber, targetUser.getId()).get();

        // then
        assertThat(notification)
                .extracting("subjectNumber", "subjectName", "professor")
                .contains(targetSubjectNumber, "모바일 프로그래밍", "professor1");
    }

    @Test
    @DisplayName("과목번호와 유저를 통해 알림을 조회한다. - 존재하지 않는 경우")
    void findBySubjectNumberAndUserId() {
        // when, then
        assertThat(notificationRepository.findBySubjectNumberAndUserId("empty", -1L)).isEmpty();
    }

    @Test
    @DisplayName("과목번호와 유저 아이디가 포함된 알림의 존재 여부를 확인한다.")
    void existsBySubjectNumberAndUserId() {
        // given
        String targetSubjectNumber = "0001";
        User user1 = createUser();
        User targetUser = userRepository.save(user1);

        Notification notification1 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", targetUser);
        notificationRepository.save(notification1);

        // when
        boolean result = notificationRepository.existsBySubjectNumberAndUserId(targetSubjectNumber, targetUser.getId());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("과목번호가 포함된 알림의 존재 여부를 확인한다.")
    void existsBySubjectNumber() {
        // given
        String targetSubjectNumber = "0001";
        User user1 = createUser();
        User user = userRepository.save(user1);

        Notification notification1 = createNotification(targetSubjectNumber, "모바일 프로그래밍", "professor1", user);
        notificationRepository.save(notification1);

        // when
        boolean result = notificationRepository.existsBySubjectNumber(targetSubjectNumber);

        // then
        assertThat(result).isTrue();
    }

    private static Notification createNotification(String subjectNumber, String subjectName, String professor, User user) {
        return Notification.builder()
                .subjectNumber(subjectNumber)
                .subjectName(subjectName)
                .professor(professor)
                .user(user)
                .build();
    }

    private static User createUser() {
        return User.builder()
                .fcmToken("testToken")
                .build();
    }

}