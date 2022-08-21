package dev.checku.checkuserver.domain.topic.application;

import dev.checku.checkuserver.domain.topic.entity.Topic;
import dev.checku.checkuserver.domain.topic.repository.TopicRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional
    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public Boolean existsBySubjectNumber(String subjectNumber) {
        return topicRepository.existsBySubjectNumber(subjectNumber);
    }

    @Transactional
    public void deleteTopicBySubjectNumber(String subjectNumber) {
        Topic topic = topicRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TOPIC_NOT_FOUND));

        topicRepository.delete(topic);
    }

}
