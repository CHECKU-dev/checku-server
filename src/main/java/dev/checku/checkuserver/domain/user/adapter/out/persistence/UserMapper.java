package dev.checku.checkuserver.domain.user.adapter.out.persistence;

import dev.checku.checkuserver.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToDomainEntity(UserJpaEntity userJpaEntity) {
        return User.builder()
                .id(userJpaEntity.getId())
                .pushToken(userJpaEntity.getPushToken())
                .build();
    }
}
