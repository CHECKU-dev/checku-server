package dev.checku.checkuserver.model;

import lombok.Getter;

@Getter
public enum Grade {

    ALL(""), FIRST("1"), SECOND("2"), THIRD("3"), FOURTH("4");

    private String value;

    Grade(String value) {
        this.value = value;
    }

}
