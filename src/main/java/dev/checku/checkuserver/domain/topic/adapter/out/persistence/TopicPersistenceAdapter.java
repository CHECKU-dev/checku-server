package dev.checku.checkuserver.domain.topic.adapter.out.persistence;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.topic.application.port.out.CreateTopicPort;
import dev.checku.checkuserver.domain.topic.domain.Topic;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TopicPersistenceAdapter implements CreateTopicPort {

    private final TopicJpaRepository topicJpaRepository;
    private final TopicMapper topicMapper;

    @Override
    public Topic create(String subjectNumber) {
        TopicJpaEntity topicJpaEntity = topicJpaRepository.save(new TopicJpaEntity(subjectNumber));
        return topicMapper.mapToDomainEntity(topicJpaEntity);
    }
}
