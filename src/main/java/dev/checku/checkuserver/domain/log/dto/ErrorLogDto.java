package dev.checku.checkuserver.domain.log.dto;

import dev.checku.checkuserver.domain.log.entity.ErrorLog;
import dev.checku.checkuserver.domain.log.entity.Log;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLogDto {

    private int status;

    private String errorMessage;

    public ErrorLog toEntity() {
        return ErrorLog.builder()
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }

    public static ErrorLogDto of(int status, String errorMessage) {
        return ErrorLogDto.builder()
                .status(status)
                .errorMessage(errorMessage)
                .build();
    }

}
