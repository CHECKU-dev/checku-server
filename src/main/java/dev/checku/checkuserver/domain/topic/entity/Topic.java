package dev.checku.checkuserver.domain.topic.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "topic")
@Getter
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @Column(nullable = false, unique = true)
    private String subjectNumber;

    @Builder
    public Topic(String subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public static Topic createTopic(String subjectNumber) {

        return Topic.builder()
                .subjectNumber(subjectNumber)
                .build();
    }

}
