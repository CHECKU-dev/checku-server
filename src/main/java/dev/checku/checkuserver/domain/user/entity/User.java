package dev.checku.checkuserver.domain.user.entity;

import dev.checku.checkuserver.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fcmToken;


    @Builder
    public User(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateFCM(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
