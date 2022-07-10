package dev.checku.checkuserver.domain.checku.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class SubjectDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "학과는 필수값 입니다.")
        private String department;

        private String grade;

        private String type;

    }

    //TODO 분리
    @Getter @Setter
    public static class Response {

        // 학년 *
        private Integer grade;

        // 교수명
        private String professor;

        // 과목명 *
        private String name;

        // 현재 인원/제한인원 *
        private String numberOfPeople;

        // 비고 *
        private String remark;

//        // 강의요시(강의실)
//        private String timeAndPlace;

        // 이수구분 *
        private String subjectType;

        // 학과
        private String department;

        // 과목번호 *
        private String subjectNumber;

        @Builder
        public Response(Integer grade, String professor, String name, String numberOfPeople,
                        String remark, String subjectType, String department, String subjectNumber) {
            this.grade = grade;
            this.professor = professor;
            this.name = name;
            this.numberOfPeople = numberOfPeople;
            this.remark = remark;
            this.subjectType = subjectType;
            this.department = department;
            this.subjectNumber = subjectNumber;
        }


        public static Response of(PortalRes.SubjectDto subject) {

            return Response.builder()
                    .grade(subject.getGrade())
                    .professor(subject.getProfessor())
                    .name(subject.getName())
                    .numberOfPeople(subject.getNumberOfPeople())
                    .remark(subject.getRemark())
                    .subjectType(subject.getSubjectType())
                    .department(subject.getDepartment())
                    .subjectNumber(subject.getSubjectNumber())
                    .build();
        }



    }

}
