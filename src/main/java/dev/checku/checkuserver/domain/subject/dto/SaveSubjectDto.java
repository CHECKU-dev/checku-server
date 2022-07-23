package dev.checku.checkuserver.domain.subject.dto;

import dev.checku.checkuserver.domain.subject.entity.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SaveSubjectDto {

    @Getter
    @Setter
    public static class Request {

        private Long userId;

        private String subjectNumber;
    }

    @Getter
    @Setter
    public static class Response {

        private String message;

        @Builder
        public Response(Long saveSubjectId) {
            this.message = message;
        }

        public static Response from(Subject subject) {
            return Response.builder()
                    .saveSubjectId(subject.getSubjectId())
                    .build();

        }
    }

}
