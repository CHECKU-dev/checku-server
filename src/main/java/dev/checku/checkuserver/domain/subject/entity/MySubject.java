package dev.checku.checkuserver.domain.subject.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "my_subject")
@Getter
@NoArgsConstructor
public class MySubject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mySubjectId;

    @Column(nullable = false)
    private String subjectNumber;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public MySubject(String subjectNumber, User user) {
        this.subjectNumber = subjectNumber;
        this.user = user;
    }

    public static MySubject createSubject(String subjectNumber, User user) {

        return MySubject.builder()
                .subjectNumber(subjectNumber)
                .user(user)
                .build();
    }




}
