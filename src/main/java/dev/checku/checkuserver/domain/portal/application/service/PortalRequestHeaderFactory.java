package dev.checku.checkuserver.domain.portal.application.service;

import dev.checku.checkuserver.domain.session.application.port.out.GetPortalSessionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PortalRequestHeaderFactory {

    private final GetPortalSessionPort getPortalSessionPort;

    public Map<String, String> create() {
        Map<String, String> header = new HashMap<>();
        header.put("Referer", "https://kuis.konkuk.ac.kr/index.do");
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.80 Safari/537.36");
        header.put("Cookie", "JSESSIONID=" + getPortalSessionPort.get().getValue());

        return header;
    }
}
