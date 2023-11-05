package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

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
public class BookmarkJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @Column(nullable = false)
    private SubjectNumber subjectNumber;

    private BookmarkJpaEntity(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        this.userJpaEntity = userJpaEntity;
        this.subjectNumber = subjectNumber;
    }

    public static BookmarkJpaEntity create(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        return new BookmarkJpaEntity(userJpaEntity, subjectNumber);
    }
}
