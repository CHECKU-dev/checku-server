package dev.checku.checkuserver.domain.topic.entity;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SubjectNumber subjectNumber;

    @Builder
    public Topic(SubjectNumber subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public static Topic create(SubjectNumber subjectNumber) {
        return Topic.builder()
                .subjectNumber(subjectNumber)
                .build();
    }
}
