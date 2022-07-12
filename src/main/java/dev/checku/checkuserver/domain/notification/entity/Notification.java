package dev.checku.checkuserver.domain.notification.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private String subjectNumber;

    @Column(nullable = false)
    private String subjectName;


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder
    public Notification(String subjectNumber,String subjectName, User user) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.user = user;
    }

    public static Notification createNotification(Notification notification, User user) {

        return Notification.builder()
                .subjectName(notification.getSubjectName())
                .subjectNumber(notification.getSubjectNumber())
                .user(user)
                .build();
    }

}
