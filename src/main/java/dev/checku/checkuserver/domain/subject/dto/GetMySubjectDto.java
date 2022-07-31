package dev.checku.checkuserver.domain.subject.dto;

import dev.checku.checkuserver.global.util.timeutils.TimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMySubjectDto {

    @Getter
    @Setter
    public static class Request {

        @NotNull
        private Long userId;

    }

    @Getter
    @Setter
    public static class Response {

        // 학년 *
        private String grade;

        // 교수명
        private String professor;

        // 과목명 *
        private String subjectName;

        // 남은 인원 *
        private String emptySeat;

        // 현재 인원/제한인원 *
        private String numberOfPeople;

        // 비고 *
        private String remark;

        // 강의요시(강의실)
        private String timeAndPlace;

        // 이수구분 *
        private String subjectType;

        // 학과
        private String department;

        // 과목번호 *
        private String subjectNumber;

        @Builder
        public Response(String grade, String professor, String subjectName, String numberOfPeople, String emptySeat,
                        String remark, String timeAndPlace, String subjectType, String department, String subjectNumber) {
            this.grade = grade;
            this.professor = professor;
            this.subjectName = subjectName;
            this.numberOfPeople = numberOfPeople;
            this.emptySeat = emptySeat;
            this.remark = remark;
            this.timeAndPlace = timeAndPlace;
            this.subjectType = subjectType;
            this.department = department;
            this.subjectNumber = subjectNumber;
        }


        public static Response from(PortalRes.SubjectDto subjectDto) {
            // 교시를 시간으로 변경
            //TODO 정리
            if (subjectDto.getTimeAndPlace() != null) {
                Pattern pattern = Pattern.compile("\\d{2}-\\d{2}");
                Matcher matcher = pattern.matcher(subjectDto.getTimeAndPlace());
                while (matcher.find()) {
                    String period = matcher.group();
                    String[] periodArr = period.split("-");
                    String startPeriod = periodArr[0];
                    String endPeriod = periodArr[1];
                    subjectDto.setTimeAndPlace(
                            subjectDto.getTimeAndPlace().replace(
                                    period,
                                    TimeUtils.toStartHour(startPeriod) + "-" + TimeUtils.toEndHour(endPeriod)
                            )
                    );
                }
            }

            // TODO 유틸로 묶기 이전에 empty 찾던거랑 같이
            String[] findEmpty = subjectDto.getNumberOfPeople().split("/");
            String emptySeat = String.valueOf(Integer.parseInt(findEmpty[1]) - Integer.parseInt(findEmpty[0]));

            return Response.builder()
                    .grade(subjectDto.getGrade().equals("9") ? "전체" : subjectDto.getGrade())
                    .professor(subjectDto.getProfessor())
                    .subjectName(subjectDto.getName())
                    .numberOfPeople(subjectDto.getNumberOfPeople())
                    .emptySeat(emptySeat)
                    .remark(subjectDto.getRemark())
                    .timeAndPlace(subjectDto.getTimeAndPlace())
                    .subjectType(subjectDto.getSubjectType())
                    .department(subjectDto.getDepartment())
                    .subjectNumber(subjectDto.getSubjectNumber())
                    .build();
        }
    }

}