package dev.checku.checkuserver.domain.bookmark.entity;

import dev.checku.checkuserver.domain.common.adapter.out.persistence.BaseTimeJpaEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @Column(nullable = false)
    private SubjectNumber subjectNumber;

    private Bookmark(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        this.userJpaEntity = userJpaEntity;
        this.subjectNumber = subjectNumber;
    }

    public static Bookmark create(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        return new Bookmark(userJpaEntity, subjectNumber);
    }
}
