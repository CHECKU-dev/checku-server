package dev.checku.checkuserver.domain.user.application.port.out;

import dev.checku.checkuserver.domain.user.domain.User;

public interface CreateUserPort {

    User create(String pushToken);
}
