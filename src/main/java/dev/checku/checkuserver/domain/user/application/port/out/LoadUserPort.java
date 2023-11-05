package dev.checku.checkuserver.domain.user.application.port.out;

import dev.checku.checkuserver.domain.user.domain.User;

import java.util.Optional;

public interface LoadUserPort {

    Optional<User> findByPushToken(String pushToken);

    User getById(Long id);
}
