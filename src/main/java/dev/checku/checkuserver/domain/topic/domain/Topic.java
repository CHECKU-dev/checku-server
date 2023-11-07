package dev.checku.checkuserver.domain.topic.domain;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import lombok.Builder;

public class Topic {

    private Long id;

    private SubjectNumber subjectNumber;

    @Builder
    public Topic(Long id, SubjectNumber subjectNumber) {
        this.id = id;
        this.subjectNumber = subjectNumber;
    }
}
