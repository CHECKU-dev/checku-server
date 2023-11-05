package dev.checku.checkuserver.domain.user.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserPersistenceAdapter;
import dev.checku.checkuserver.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserService {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public User getById(Long id) {
        return userPersistenceAdapter.getById(id);
    }
}
