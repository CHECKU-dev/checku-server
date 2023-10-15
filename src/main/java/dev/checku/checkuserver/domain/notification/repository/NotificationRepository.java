package dev.checku.checkuserver.domain.notification.repository;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);

    @Query("select n " +
            "from Notification n " +
            "join fetch n.user u " +
            "where n.subjectNumber=:subjectNumber ")
    List<Notification> findAllBySubjectNumber(SubjectNumber subjectNumber);

    Optional<Notification> findByUserAndSubjectNumber(User user, SubjectNumber subjectNumber);

    boolean existsByUserAndSubjectNumber(User user, SubjectNumber subjectNumber);

    boolean existsBySubjectNumber(SubjectNumber subjectNumber);
}
