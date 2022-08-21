package dev.checku.checkuserver.infra.portal;

import dev.checku.checkuserver.global.util.PortalUtils;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PortalFeignClient portalFeignClient;

    public String getSession() {
        Response response;
        String cookie = " ";
        response = portalFeignClient.getSession();

        for (int i = 0; i < 3; i++) {
            if (StringUtils.hasText(response.headers().get("set-cookie").toString())) {
                cookie = response.headers().get("set-cookie").toString();
                System.out.println(cookie);
                break;
            }else {
                System.out.println("check");
                response = portalFeignClient.getSession();
            }
        }

        String jSessionId = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        return jSessionId;
    }

    public String login() {
        String session = "JSESSIONID=" + getSession();
        ResponseEntity<String> response = portalFeignClient.login(
                session,
                PortalUtils.header,
                PortalUtils.body
        );

        // 로그인 성공 시
        if ("{\"_METADATA_\":{\"success\":true}}".equals(response.getBody())) {
            return session;
        } else {
            return "";
        }
    }
}
