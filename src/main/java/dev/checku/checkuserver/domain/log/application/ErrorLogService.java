package dev.checku.checkuserver.domain.log.application;

import dev.checku.checkuserver.domain.log.repository.ErrorLogRepository;
import dev.checku.checkuserver.domain.log.dto.ErrorLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    @Transactional
    public void saveErrorLog(ErrorLogDto errorLogDto) {
        errorLogRepository.save(errorLogDto.toEntity());
    }

}
