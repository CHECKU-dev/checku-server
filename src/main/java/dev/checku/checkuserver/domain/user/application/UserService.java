package dev.checku.checkuserver.domain.user.application;

import dev.checku.checkuserver.domain.user.repository.UserRepository;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.domain.user.dto.UserLoginDto;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
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

        String fcmToken = loginRequestDto.getFcmToken();
        Optional<User> userOptional = userRepository.findByFcmToken(fcmToken);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // FCM 토큰이 DB에 없다면 유저 신규 등록
            User saveUser = loginRequestDto.toEntity();
            user = userRepository.save(saveUser);
        }

        return UserLoginDto.Response.of(user);
    }

    // TODO 다음 업데이트 반영
//    @Transactional
//    public UserLoginDto.Response login(UserLoginDto.Request loginRequestDto) {
//        String fcmToken = loginRequestDto.getFcmToken();
//        User user;
//        // 처음
//        if (loginRequestDto.getUserId() == 0L) {
//            User saveUser = loginRequestDto.toEntity();
//            user = userRepository.save(saveUser);
//        } else {
//            Optional<User> optionalUser = userRepository.findByFcmToken(fcmToken);
//            if (optionalUser.isPresent()) {
//                user = optionalUser.get();
//            } else {
//                // FCM 토큰이 DB에 없다면 유저 FCM 업데이트
//                User getUser = userRepository.findById(loginRequestDto.getUserId())
//                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
//                getUser.updateFCM(loginRequestDto.getFcmToken());
//                user = getUser;
//            }
//        }
//        return UserLoginDto.Response.of(user);
//    }

    public User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    }


}
