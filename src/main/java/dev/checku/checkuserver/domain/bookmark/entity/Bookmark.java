package dev.checku.checkuserver.domain.bookmark.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private SubjectNumber subjectNumber;

    private Bookmark(User user, SubjectNumber subjectNumber) {
        this.user = user;
        this.subjectNumber = subjectNumber;
    }

    public static Bookmark create(User user, SubjectNumber subjectNumber) {
        return new Bookmark(user, subjectNumber);
    }
}
