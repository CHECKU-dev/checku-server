package dev.checku.checkuserver.checku.application;

import dev.checku.checkuserver.global.util.Values;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PortalFeignClient portalFeignClient;

    public String getSession() {
        Response response = portalFeignClient.getSession();
        String cookie = response.headers().get("set-cookie").toString();
        String jSessionId = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        return jSessionId;
    }

    public String login() {

        String session = "JSESSIONID=" + getSession();

        portalFeignClient.login(
                session,
                Values.headers,
                Values.body
        );

        return session;
    }
}
