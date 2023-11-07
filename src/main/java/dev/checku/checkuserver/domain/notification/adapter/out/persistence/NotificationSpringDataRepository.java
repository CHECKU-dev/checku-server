package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationSpringDataRepository extends JpaRepository<NotificationJpaEntity, Long> {

    long deleteByUserIdAndSubjectNumber(Long userId, String subjectNumber);

    List<NotificationJpaEntity> findAllByUserId(Long userId);
}
