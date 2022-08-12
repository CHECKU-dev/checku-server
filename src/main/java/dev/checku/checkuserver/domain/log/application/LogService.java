package dev.checku.checkuserver.domain.log.application;

import dev.checku.checkuserver.domain.log.dao.LogRepository;
import dev.checku.checkuserver.domain.log.dto.LogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogService {

    private final LogRepository logRepository;

    @Transactional
    public void saveLog(LogDto logdto) {
        logRepository.save(logdto.toEntity());
    }

}
