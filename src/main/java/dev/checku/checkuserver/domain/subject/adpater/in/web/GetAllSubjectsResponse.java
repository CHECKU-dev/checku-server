package dev.checku.checkuserver.domain.subject.adpater.in.web;

import dev.checku.checkuserver.domain.temp.PortalSubjectResponse;
import dev.checku.checkuserver.domain.subject.domain.Grade;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.global.util.PortalResponseParser;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

public class GetAllSubjectsResponse {

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
    private Boolean bookmarked;

    @Builder
    private GetAllSubjectsResponse(String grade, String professor, String subjectName, String numberOfPeople, String emptySeat,
                    String remark, String timeAndPlace, String subjectType, String department, String subjectNumber, Boolean bookmarked) {
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
        this.bookmarked = bookmarked;
    }

    public static GetAllSubjectsResponse from(PortalSubjectResponse.SubjectDetail subjectDetail, List<SubjectNumber> bookmarks) {
        String originalTimeAndPlace = subjectDetail.getTimeAndPlace();
        String convertedTimeAndPlace = PortalResponseParser.convertPeriodsToHours(originalTimeAndPlace);

        String emptySeat = PortalResponseParser.extractEmptySeat(subjectDetail);

        String professor = Optional.ofNullable(subjectDetail.getProfessor())
                .map(String::trim)
                .orElse(null);

        return GetAllSubjectsResponse.builder()
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
                .bookmarked(bookmarks.contains(new SubjectNumber(subjectDetail.getSubjectNumber())))
                .build();
    }
}
