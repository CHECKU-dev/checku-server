package dev.checku.checkuserver.domain.topic.repository;

import dev.checku.checkuserver.domain.topic.entity.Topic;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsBySubjectNumber(SubjectNumber subjectNumber);

    Optional<Topic> findBySubjectNumber(SubjectNumber subjectNumber);
}
