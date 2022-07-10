package dev.checku.checkuserver.domain.user.application;

import dev.checku.checkuserver.domain.user.dao.UserRepository;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.domain.user.dto.UserLoginDto;
import dev.checku.checkuserver.global.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserLoginDto.Response login(UserLoginDto.Request loginRequestDto) {
        Long userId;

        String fcmToken = loginRequestDto.getFcmToken();
        Optional<User> userOptional = userRepository.findByFcmToken(fcmToken);

        if (userOptional.isPresent()) {
            userId = userOptional.get().getId();
        } else {
            // FCM 토큰이 DB에 없다면 유저 신규 등록
            User savedUser = userRepository.save(loginRequestDto.toEntity());
            userId = savedUser.getId();
        }

        return UserLoginDto.Response.builder()
                .userId(userId)
                .fcmToken(fcmToken)
                .build();
    }

    public User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    }


}
