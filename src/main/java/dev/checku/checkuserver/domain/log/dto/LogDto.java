package dev.checku.checkuserver.domain.log.dto;

import dev.checku.checkuserver.domain.log.entity.Log;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDto {

    private String methodName;

    private String params;

    private Long executionTime;

    public Log toEntity() {
        return Log.builder()
                .methodName(methodName)
                .params(params)
                .executionTime(executionTime)
                .build();
    }

    public static LogDto of(String methodName, String params, Long executionTime) {
        return LogDto.builder()
                .methodName(methodName)
                .params(params)
                .executionTime(executionTime)
                .build();
    }

}
