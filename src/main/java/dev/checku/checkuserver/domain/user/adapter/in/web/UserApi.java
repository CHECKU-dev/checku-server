package dev.checku.checkuserver.domain.user.adapter.in.web;

import dev.checku.checkuserver.domain.user.application.port.in.LoginUseCase;
import dev.checku.checkuserver.domain.user.domain.User;
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

    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        User user = loginUseCase.login(request.getPushToken());
        LoginResponse response = new LoginResponse(user);
        return ResponseEntity.ok(response);
    }
}
