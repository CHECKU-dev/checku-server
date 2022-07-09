package dev.checku.checkuserver.application;

import dev.checku.checkuserver.domain.user.dao.UserRepository;
import dev.checku.checkuserver.domain.user.domain.User;
import dev.checku.checkuserver.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
