package dev.checku.checkuserver;

import dev.checku.checkuserver.domain.portal.dto.PortalRes;
import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.enums.Department;
import dev.checku.checkuserver.domain.subject.enums.Grade;
import dev.checku.checkuserver.domain.subject.enums.Type;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public String healthCheck() {
        return "I'm OK...";
    }


}
