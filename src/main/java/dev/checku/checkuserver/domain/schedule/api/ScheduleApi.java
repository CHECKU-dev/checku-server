package dev.checku.checkuserver.domain.schedule.api;

import dev.checku.checkuserver.domain.schedule.application.ScheduleService;
import dev.checku.checkuserver.domain.schedule.dto.GetScheduleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleApi {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> get() {
        return ResponseEntity.ok(scheduleService.get());
    }
}
