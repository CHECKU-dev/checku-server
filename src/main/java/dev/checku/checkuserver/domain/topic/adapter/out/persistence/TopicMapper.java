package dev.checku.checkuserver.domain.topic.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.domain.topic.domain.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public Topic mapToDomainEntity(TopicJpaEntity topicJpaEntity) {
        return Topic.builder()
                .id(topicJpaEntity.getId())
                .subjectNumber(new SubjectNumber(topicJpaEntity.getSubjectNumber()))
                .build();
    }
}
