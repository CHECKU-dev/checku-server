package dev.checku.checkuserver.domain.schedule.adpater.in.web;

import dev.checku.checkuserver.domain.schedule.application.service.ScheduleService;
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
