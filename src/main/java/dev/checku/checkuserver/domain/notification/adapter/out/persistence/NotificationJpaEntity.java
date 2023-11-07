package dev.checku.checkuserver.domain.notification.adapter.out.persistence;

import dev.checku.checkuserver.domain.common.adapter.out.persistence.BaseTimeJpaEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String subjectNumber;

    public NotificationJpaEntity(Long userId, String subjectNumber) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
    }
}
