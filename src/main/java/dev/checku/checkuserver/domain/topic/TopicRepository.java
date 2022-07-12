package dev.checku.checkuserver.domain.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Boolean existsBySubjectNumber(String subjectNumber);

    Optional<Topic> findBySubjectNumber(String subjectNumber);
}
