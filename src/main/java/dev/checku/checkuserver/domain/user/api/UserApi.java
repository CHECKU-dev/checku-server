package dev.checku.checkuserver.domain.user.api;

import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserLoginDto.Response> userLogin(
            @RequestBody @Valid UserLoginDto.Request loginRequestDto
    ) {
        UserLoginDto.Response loginResponseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

}
