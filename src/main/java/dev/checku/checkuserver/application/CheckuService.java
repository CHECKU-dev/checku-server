package dev.checku.checkuserver.application;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckuService {

    private final PortalFeignClient portalFeignClient;

    public String getSession() {
        Response response = portalFeignClient.getSession();
        String cookie = response.headers().get("set-cookie").toString();
        String jSessionId = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        return jSessionId;
    }

}
