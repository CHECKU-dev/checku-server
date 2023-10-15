package dev.checku.checkuserver.domain.user.application;

import dev.checku.checkuserver.domain.user.dto.LoginRequest;
import dev.checku.checkuserver.domain.user.dto.LoginResponse;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.domain.user.repository.UserRepository;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        String fcmToken = request.getPushToken();
        User user = getOrCreateUser(fcmToken, request);
        return new LoginResponse(user.getId(), user.getPushToken());
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getBy(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private User getOrCreateUser(String fcmToken, LoginRequest request) {
        return userRepository.findByPushToken(fcmToken)
                .orElseGet(() -> createUser(request));
    }

    private User createUser(LoginRequest request) {
        User newUser = request.toEntity();
        return saveUser(newUser);
    }
}
