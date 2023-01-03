package dev.checku.checkuserver.domain.subject.dto;

import dev.checku.checkuserver.domain.model.Grade;
import dev.checku.checkuserver.domain.portal.PortalRes;
import dev.checku.checkuserver.global.util.timeutils.TimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSearchSubjectDto {

    @Getter
    @Setter
    public static class Request {

        @NotNull
        private Long userId;

        @NotBlank
        private String searchQuery;
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

        private Boolean isMySubject;

        @Builder
        public Response(String grade, String professor, String subjectName, String numberOfPeople, String emptySeat,
                        String remark, String timeAndPlace, String subjectType, String department, String subjectNumber, Boolean isMySubject) {
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
            this.isMySubject = isMySubject;

        }

        // TODO 정리
        public static Response from(PortalRes.SubjectDto subjectDto, List<String> subjectList) {
            // 교시를 시간으로 변경
            if (subjectDto.getTimeAndPlace() != null) {
                Pattern pattern = Pattern.compile("\\d{2}-\\d{2}");
                Matcher matcher = pattern.matcher(subjectDto.getTimeAndPlace());
                while (matcher.find()) {
                    String period = matcher.group();
                    String[] periodArr = period.split("-");
                    String startPeriod = periodArr[0];
                    String endPeriod = periodArr[1];
                    subjectDto.setTimeAndPlace(
                            subjectDto.getTimeAndPlace()
                                    .replace(period, TimeUtils.toStartHour(startPeriod) + "-" + TimeUtils.toEndHour(endPeriod))
                                    .replaceAll("\\([^()]+\\)", "").trim()
                    );
                }
            }

            String[] findEmpty = subjectDto.getNumberOfPeople().split("/");
            String emptySeat = String.valueOf(Integer.parseInt(findEmpty[1]) - Integer.parseInt(findEmpty[0]));

            return Response.builder()
                    .grade(Grade.of(subjectDto.getGrade()).getDescription())
                    .grade(subjectDto.getGrade().equals(9) ? "전체" : subjectDto.getGrade() + "학년")
                    .professor(subjectDto.getProfessor() != null ? subjectDto.getProfessor().trim() : subjectDto.getProfessor())
                    .subjectName(subjectDto.getName())
                    .numberOfPeople(subjectDto.getNumberOfPeople())
                    .emptySeat(emptySeat)
                    .remark(subjectDto.getRemark())
                    .timeAndPlace(subjectDto.getTimeAndPlace())
                    .subjectType(subjectDto.getSubjectType())
                    .department(subjectDto.getDepartment())
                    .subjectNumber(subjectDto.getSubjectNumber())
                    .isMySubject(subjectList.contains(subjectDto.getSubjectNumber()))
                    .build();
        }
    }

}
