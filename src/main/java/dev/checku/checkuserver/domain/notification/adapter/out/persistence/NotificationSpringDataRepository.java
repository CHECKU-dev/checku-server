package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import dev.checku.checkuserver.domain.notification.adapter.out.persistence.NotificationJpaEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationSpringDataRepository extends JpaRepository<NotificationJpaEntity, Long> {
    List<NotificationJpaEntity> findAllByUserJpaEntityId(Long userId);

    @Query("select n " +
            "from NotificationJpaEntity n " +
            "join fetch n.userJpaEntity u " +
            "where n.subjectNumber=:subjectNumber ")
    List<NotificationJpaEntity> findAllBySubjectNumber(SubjectNumber subjectNumber);

    Optional<NotificationJpaEntity> findByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

    boolean existsByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

    boolean existsBySubjectNumber(SubjectNumber subjectNumber);
}
