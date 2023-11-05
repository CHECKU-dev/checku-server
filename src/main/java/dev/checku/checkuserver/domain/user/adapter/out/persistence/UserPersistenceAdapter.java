package dev.checku.checkuserver.domain.user.adapter.out.persistence;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.user.application.port.out.CreateUserPort;
import dev.checku.checkuserver.domain.user.application.port.out.LoadUserPort;
import dev.checku.checkuserver.domain.user.domain.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements CreateUserPort, LoadUserPort {

    private final UserSpringDataRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User create(String pushToken) {
        UserJpaEntity userJpaEntity = userRepository.save(new UserJpaEntity(pushToken));
        return userMapper.mapToDomainEntity(userJpaEntity);
    }

    @Override
    public Optional<User> findByPushToken(String pushToken) {
        return userRepository.findByPushToken(pushToken)
                .map(userMapper::mapToDomainEntity);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDomainEntity)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
