package dev.checku.checkuserver.domain.schedule;

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
@RequestMapping("/api/schedule")
public class ScheduleApi {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getSchedule() {
        return ResponseEntity.ok(scheduleService.getSchedule());
    }

}
