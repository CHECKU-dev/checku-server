package dev.checku.checkuserver.domain.subject.entity;

import dev.checku.checkuserver.domain.portal.dto.PortalRes;
import dev.checku.checkuserver.domain.subject.enums.SubjectType;
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


    public static Subject classifyMajorOrLiberalArts(String subjectNumber, String subjectName, String subjectType) {
        Subject subject;
        if (isMajor(subjectType)) {
            subject = Subject.createSubject(subjectNumber, subjectName, SubjectType.MAJOR);
        } else {
            subject = Subject.createSubject(subjectNumber, subjectName, SubjectType.LIBERAL_ARTS);
        }
        return subject;

    }

    private static boolean isMajor(String subjectType) {
        return "전선".equals(subjectType) || "전필".equals(subjectType);
    }

}
