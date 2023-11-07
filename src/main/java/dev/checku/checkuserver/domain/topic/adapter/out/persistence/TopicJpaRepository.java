package dev.checku.checkuserver.domain.topic.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicJpaRepository extends JpaRepository<TopicJpaEntity, Long> {

    Boolean existsBySubjectNumber(SubjectNumber subjectNumber);

    Optional<TopicJpaEntity> findBySubjectNumber(SubjectNumber subjectNumber);
}
