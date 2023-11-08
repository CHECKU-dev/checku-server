package dev.checku.checkuserver.domain.subject.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public enum SubjectCategory {

    // 전공
    ALL(""), ESSENTIAL("B04044"), OPTIONAL("B04045"), OTHER(""),
    // 교양
    BASIC_ELECTIVE("B0404P"), ADVANCED_ELECTIVE("B04054"), GENERAL_ELECTIVE("B04046");

    private final String value;

    public static SubjectCategory setType(String type) {
        if (!StringUtils.hasText(type)) {
            return SubjectCategory.ALL;
        }
        return SubjectCategory.valueOf(type);
    }

    public boolean matchType(String subjectType) {
        if (this == SubjectCategory.ALL) return true; // 전체는 필터링 X
        else if (this == SubjectCategory.ESSENTIAL || this == SubjectCategory.OPTIONAL) return true; // 이미 정렬

        return !subjectType.equals("전필") && !subjectType.equals("전선");
    }
}
