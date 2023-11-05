package dev.checku.checkuserver.domain.topic.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.topic.adapter.out.persistence.TopicPersistenceAdapter;
import dev.checku.checkuserver.domain.topic.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class CreateTopicService {

    private TopicPersistenceAdapter topicPersistenceAdapter;

    public Topic create(String subjectNumber) {
        return topicPersistenceAdapter.create(subjectNumber);
    }
}
