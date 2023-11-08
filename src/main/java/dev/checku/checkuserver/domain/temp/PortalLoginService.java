package dev.checku.checkuserver.domain.temp;

import dev.checku.checkuserver.domain.portal.application.port.out.GetPortalSubjectDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalLoginService {

    private static final String SET_COOKIE_HEADER = "set-cookie";
    private static final String LOGIN_SUCCESS_RESPONSE = "{\"_METADATA_\":{\"success\":true}}";


    private final PortalRequestFactory portalRequestFactory;
    private final GetPortalSubjectDetailPort getPortalSubjectDetailPort;

    public String login() {
        ResponseEntity<String> response = getPortalSubjectDetailPort.login(
                portalRequestFactory.createHeader(),
                portalRequestFactory.body
        );

        if (isLoginSuccess(response.getBody())) {
            String cookie = response.getHeaders().get(SET_COOKIE_HEADER).toString();
            return "JSESSIONID=" + extractSessionId(cookie);
        } else {
            return "";
        }
    }

    private String extractSessionId(String cookie) {
        return cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
    }

    private boolean isLoginSuccess(String responseBody) {
        return LOGIN_SUCCESS_RESPONSE.equals(responseBody);
    }
}
