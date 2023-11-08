package dev.checku.checkuserver.domain.subject.domain;

import lombok.Value;

@Value
public class SearchCondition {

    Department department;

    Grade grade;

    SubjectCategory subjectCategory;

    boolean onlyEmptySeat;
}
