package dev.checku.checkuserver.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Grade {

//    ALL(""), FIRST("1"), SECOND("2"), THIRD("3"), FOURTH("4");
//
//    private String value;
//
//    Grade(String value) {
//        this.value = value;
//    }

    FIRST(1, "1학년"),
    SECOND(2, "2학년"),
    THIRD(3, "3학년"),
    FOURTH(4, "4학년"),
    ALL(9, "전체"),
    UNKNOWN(null, "");

    private final Integer grade;
    private final String description;

    public static Grade of(Integer grade) {
        return Arrays.stream(values())
                .filter(subjectGrade -> subjectGrade.grade.equals(grade))
                .findAny()
                .orElse(UNKNOWN);
    }

}
