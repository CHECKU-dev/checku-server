package dev.checku.checkuserver.domain.subject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Grade {

    FIRST(1, "1학년"),
    SECOND(2, "2학년"),
    THIRD(3, "3학년"),
    FOURTH(4, "4학년"),
    ALL(9, "전체"),
    UNKNOWN(null, "");

    private final Integer number;
    private final String description;

    public static Grade of(Integer grade) {
        return Arrays.stream(values())
                .filter(subjectGrade -> subjectGrade.number.equals(grade))
                .findAny()
                .orElse(UNKNOWN);
    }

    public static Grade setNumber(String grade) {
        if (StringUtils.hasText(grade)) {
            return Grade.valueOf(grade.toUpperCase());
        }
        return Grade.ALL;
    }

    public boolean matchGrade(Integer grade) {
        if (this == Grade.ALL) return true;
        return this.getNumber().equals(grade);
    }
}
