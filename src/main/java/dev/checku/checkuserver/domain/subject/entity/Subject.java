package dev.checku.checkuserver.domain.subject.entity;

import dev.checku.checkuserver.domain.model.SubjectType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@Getter
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(nullable = false, unique = true)
    private String subjectNumber;

    @Column(nullable = false)
    private String subjectName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectType subjectType;

    @Builder
    public Subject(String subjectNumber,String subjectName, SubjectType subjectType) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    public static Subject createSubject(String subjectNumber,String subjectName, SubjectType subjectType) {
        return Subject.builder()
                .subjectNumber(subjectNumber)
                .subjectName(subjectName)
                .subjectType(subjectType)
                .build();
    }






}
