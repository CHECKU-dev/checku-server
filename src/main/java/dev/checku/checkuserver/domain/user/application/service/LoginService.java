package dev.checku.checkuserver.domain.user.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserPersistenceAdapter;
import dev.checku.checkuserver.domain.user.application.port.in.LoginUseCase;
import dev.checku.checkuserver.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    @Override
    public User login(String pushToken) {
        return userPersistenceAdapter.findByPushToken(pushToken)
                .orElseGet(() -> userPersistenceAdapter.create(pushToken));
    }
}
