package dev.checku.checkuserver.domain.log.application;

import dev.checku.checkuserver.domain.log.repository.LogRepository;
import dev.checku.checkuserver.domain.log.dto.LogSearchDto;
import dev.checku.checkuserver.domain.log.dto.LogSaveDto;
import dev.checku.checkuserver.domain.log.entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogService {

    private final LogRepository logRepository;

    @Transactional
    public void saveLog(LogSaveDto logdto) {
        logRepository.save(logdto.toEntity());
    }

    public Page<LogSearchDto.Response> getLogList(Pageable pageable) {
        Page<Log> log = logRepository.findLog(pageable);
        List<LogSearchDto.Response> collect = log.stream()
                .map(LogSearchDto.Response::of)
                .collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, log.getTotalElements());
    }
}
