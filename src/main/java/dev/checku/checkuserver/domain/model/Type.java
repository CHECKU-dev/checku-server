package dev.checku.checkuserver.domain.model;

import lombok.Getter;

@Getter
public enum Type {

    // 전공
    ALL(""), ESSENTIAL("B04044"), OPTIONAL("B04045"), OTHER("other"),
    // 교양
    BASIC_ELECTIVE("B0404P"), ADVANCED_ELECTIVE("B04054"), GENERAL_ELECTIVE("B04046");

    private String value;

    Type(String value) {
        this.value = value;
    }

}
