package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.adapter.out.persistence.BaseTimeJpaEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SubjectNumber subjectNumber;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private String professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @Builder
    public NotificationJpaEntity(SubjectNumber subjectNumber, String subjectName, String professor, UserJpaEntity userJpaEntity) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.professor = professor;
        this.userJpaEntity = userJpaEntity;
    }
}
