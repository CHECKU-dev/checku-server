package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.adapter.out.persistence.BaseTimeJpaEntity;
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

    private Long userId;

    @Column(nullable = false)
    private String subjectNumber;

    private BookmarkJpaEntity(Long userId, String subjectNumber) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
    }

    public static BookmarkJpaEntity create(Long userId, String subjectNumber) {
        return new BookmarkJpaEntity(userId, subjectNumber);
    }
}
