package dev.checku.checkuserver.domain.push.api;

import dev.checku.checkuserver.domain.push.service.PushService;
import dev.checku.checkuserver.global.advice.InternalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push")
public class PushApi {

    private final PushService pushService;

    @InternalApi
    @PostMapping
    public void sendNotification(
            @RequestParam("subjectNumber") String subjectNumber
    ) {
        pushService.publish(subjectNumber);
    }
}
