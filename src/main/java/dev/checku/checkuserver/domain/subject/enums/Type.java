package dev.checku.checkuserver.domain.subject.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    // 전공
    ALL(""), ESSENTIAL("B04044"), OPTIONAL("B04045"), OTHER("other"),
    // 교양
    BASIC_ELECTIVE("B0404P"), ADVANCED_ELECTIVE("B04054"), GENERAL_ELECTIVE("B04046");

    private final String value;

}
