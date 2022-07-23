package dev.checku.checkuserver.domain.subject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class RemoveSubjectDto {

    @Getter
    @Setter
    public static class Request {

        private Long userId;

        private String subjectNumber;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {

        private String message;

        public static Response from() {
            return Response.builder()
                    .message("remove success")
                    .build();

        }
    }

}
