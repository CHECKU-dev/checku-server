package dev.checku.checkuserver.domain.notification.repository;

import dev.checku.checkuserver.domain.notification.entity.Notification;
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
    List<Notification> findAllBySubjectNumber(String subjectNumber);
    Optional<Notification> findBySubjectNumberAndUserId(String subjectNumber, Long userId);
    boolean existsBySubjectNumberAndUserId(String subjectNumber, Long userId);
    boolean existsBySubjectNumber(String subjectNumber);

}
