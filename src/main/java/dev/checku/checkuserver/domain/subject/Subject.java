package dev.checku.checkuserver.domain.subject;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.entity.User;
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

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Subject(String subjectNumber, User user) {
        this.subjectNumber = subjectNumber;
        this.user = user;
    }

    public static Subject createSubject(String subjectNumber, User user) {

        return Subject.builder()
                .subjectNumber(subjectNumber)
                .user(user)
                .build();
    }




}
