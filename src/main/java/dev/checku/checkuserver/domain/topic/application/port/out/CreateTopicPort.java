package dev.checku.checkuserver.domain.topic.application.port.out;

import dev.checku.checkuserver.domain.topic.domain.Topic;

public interface CreateTopicPort {

    Topic create(String subjectNumber);
}
