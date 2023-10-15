package dev.checku.checkuserver.domain.schedule.application;

import dev.checku.checkuserver.domain.schedule.dto.GetScheduleResponse;
import dev.checku.checkuserver.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<GetScheduleResponse> get() {
        return scheduleRepository.findAll().stream()
                .map(GetScheduleResponse::from)
                .collect(Collectors.toList());
    }
}
