package dev.checku.checkuserver.domain.topic.application;

import dev.checku.checkuserver.domain.topic.adapter.out.persistence.TopicJpaEntity;
import dev.checku.checkuserver.domain.topic.adapter.out.persistence.TopicJpaRepository;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicJpaRepository topicJpaRepository;

    @Transactional
    public void save(TopicJpaEntity topicJpaEntity) {
        topicJpaRepository.save(topicJpaEntity);
    }

    @Transactional
    public void deleteBy(SubjectNumber subjectNumber) {
        TopicJpaEntity topicJpaEntity = topicJpaRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOPIC_NOT_FOUND));

        topicJpaRepository.delete(topicJpaEntity);
    }

    public boolean existsBy(SubjectNumber subjectNumber) {
        return topicJpaRepository.existsBySubjectNumber(subjectNumber);
    }
}
