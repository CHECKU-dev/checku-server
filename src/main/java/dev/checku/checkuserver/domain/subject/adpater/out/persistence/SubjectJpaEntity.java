package dev.checku.checkuserver.domain.subject.adpater.out.persistence;

import dev.checku.checkuserver.domain.subject.domain.SubjectType;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@Getter
@NoArgsConstructor
public class SubjectJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Embedded
    private SubjectNumber subjectNumber;

    @Column(nullable = false)
    private String subjectName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectType subjectType;

    @Builder
    public SubjectJpaEntity(SubjectNumber subjectNumber, String subjectName, SubjectType subjectType) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    public static SubjectJpaEntity createSubject(SubjectNumber subjectNumber, String subjectName, SubjectType subjectType) {
        return SubjectJpaEntity.builder()
                .subjectNumber(subjectNumber)
                .subjectName(subjectName)
                .subjectType(subjectType)
                .build();
    }


    public static SubjectJpaEntity classifyMajorOrLiberalArts(SubjectNumber subjectNumber, String subjectName, String subjectType) {
        SubjectJpaEntity subjectJpaEntity;
        if (isMajor(subjectType)) {
            subjectJpaEntity = SubjectJpaEntity.createSubject(subjectNumber, subjectName, SubjectType.MAJOR);
        } else {
            subjectJpaEntity = SubjectJpaEntity.createSubject(subjectNumber, subjectName, SubjectType.LIBERAL_ARTS);
        }
        return subjectJpaEntity;

    }

    private static boolean isMajor(String subjectType) {
        return "전선".equals(subjectType) || "전필".equals(subjectType);
    }
}
