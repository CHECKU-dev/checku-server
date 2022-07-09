package dev.checku.checkuserver.api;

import dev.checku.checkuserver.application.UserService;
import dev.checku.checkuserver.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserLoginDto.Response> login(@RequestBody UserLoginDto.Request loginRequestDto) {
        UserLoginDto.Response loginResponseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

}
