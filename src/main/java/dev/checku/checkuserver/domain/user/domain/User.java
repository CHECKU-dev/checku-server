package dev.checku.checkuserver.domain.user.domain;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String fcmToken;

    @Builder
    public User(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}
