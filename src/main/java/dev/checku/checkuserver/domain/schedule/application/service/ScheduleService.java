package dev.checku.checkuserver.domain.schedule.application.service;

import dev.checku.checkuserver.domain.schedule.adpater.in.web.GetScheduleResponse;
import dev.checku.checkuserver.domain.schedule.adpater.out.persistence.ScheduleSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleSpringDataRepository scheduleSpringDataRepository;

    public List<GetScheduleResponse> get() {
        return scheduleSpringDataRepository.findAll().stream()
                .map(GetScheduleResponse::from)
                .collect(Collectors.toList());
    }
}
