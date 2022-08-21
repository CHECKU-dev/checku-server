package dev.checku.checkuserver.domain.log.api;

import dev.checku.checkuserver.domain.log.application.LogService;
import dev.checku.checkuserver.domain.log.dto.LogSearchDto;
import dev.checku.checkuserver.domain.model.OrderBy;
import dev.checku.checkuserver.global.advice.NoLogging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/log")
public class LogApi {

    private final LogService logService;

    @NoLogging
    @GetMapping
    public ResponseEntity<Page<LogSearchDto.Response>> logList(
            @Valid LogSearchDto.Request request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                20,
                request.getOrderBy() == null ?
                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
        );

        Page<LogSearchDto.Response> response = logService.getLogList(pageable);
        return ResponseEntity.ok(response);

    }



}
