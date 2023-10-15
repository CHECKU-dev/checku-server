package dev.checku.checkuserver.domain.notification.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

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
    private User user;

    @Builder
    public Notification(SubjectNumber subjectNumber, String subjectName, String professor, User user) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.professor = professor;
        this.user = user;
    }
}
