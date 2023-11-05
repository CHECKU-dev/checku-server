package dev.checku.checkuserver.domain.bookmark.adapter.in.web;

import dev.checku.checkuserver.domain.portal.adapter.in.web.PortalResponse;
import dev.checku.checkuserver.domain.subject.domain.Grade;
import dev.checku.checkuserver.global.util.PortalResponseParser;
import lombok.Builder;

import java.util.Optional;

public class GetBookmarkResponse {

    private String grade;
    private String professor;
    private String subjectName;
    private String emptySeat;
    private String numberOfPeople;
    private String remark;
    private String timeAndPlace;
    private String subjectType;
    private String department;
    private String subjectNumber;

    @Builder
    private GetBookmarkResponse(String grade, String professor, String subjectName, String numberOfPeople, String emptySeat,
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


    public static GetBookmarkResponse from(PortalResponse.SubjectDetail subjectDetail) {
        String originalTimeAndPlace = subjectDetail.getTimeAndPlace();
        String convertedTimeAndPlace = PortalResponseParser.convertPeriodsToHours(originalTimeAndPlace);

        String emptySeat = PortalResponseParser.extractEmptySeat(subjectDetail);

        String professor = Optional.ofNullable(subjectDetail.getProfessor())
                .map(String::trim)
                .orElse(null);

        return GetBookmarkResponse.builder()
                .grade(Grade.of(subjectDetail.getGrade()).getDescription())
                .professor(professor)
                .subjectName(subjectDetail.getSubjectName())
                .numberOfPeople(subjectDetail.getNumberOfPeople())
                .emptySeat(emptySeat)
                .remark(subjectDetail.getRemark())
                .timeAndPlace(convertedTimeAndPlace)
                .subjectType(subjectDetail.getSubjectType())
                .department(subjectDetail.getDepartment())
                .subjectNumber(subjectDetail.getSubjectNumber())
                .build();
    }
}
