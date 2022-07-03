package dev.checku.checkuserver.dto;

import lombok.Getter;
import lombok.Setter;

public class SubjectDto {


    @Getter @Setter
    public static class Request {

        private String department;

        private String grade;

        private String type;

    }


    public class Response {


    }
}
