package dev.checku.checkuserver.domain.notification.dao;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUser(User user);

    @Query("select n " +
            "from Notification n " +
            "join fetch n.user u " +
            "where n.subjectNumber=:subjectNumber ")
    List<Notification> findAllBySubjectNumber(String subjectNumber);

    Optional<Notification> findBySubjectNumberAndUser(String subjectNumber, User user);

    Boolean existsBySubjectNumberAndUser(String subjectNumber, User user);

    Boolean existsBySubjectNumber(String subjectNumber);

}
