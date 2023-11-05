package dev.checku.checkuserver.domain.user.application.port.in;

import dev.checku.checkuserver.domain.user.domain.User;

public interface LoginUseCase {

    User login(String pushToken);
}
