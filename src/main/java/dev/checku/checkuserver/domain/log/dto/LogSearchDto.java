package dev.checku.checkuserver.domain.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.checku.checkuserver.domain.log.entity.Log;
import dev.checku.checkuserver.domain.log.enums.OrderBy;
import dev.checku.checkuserver.global.advice.Enum;
import lombok.*;

import java.time.LocalDateTime;

public class LogSearchDto {

    @Getter
    @Setter
    public static class Request {
        @Enum(enumClass = OrderBy.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String orderBy;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String methodName;

        private String params;

        private Long executionTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        public static Response of(Log log) {
            return Response.builder()
                    .methodName(log.getMethodName())
                    .params(log.getParams())
                    .executionTime(log.getExecutionTime())
                    .createTime(log.getCreateAt())
                    .build();
        }
    }
}
