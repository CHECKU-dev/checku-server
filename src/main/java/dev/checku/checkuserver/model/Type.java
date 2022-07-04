package dev.checku.checkuserver.model;

import lombok.Getter;

@Getter
public enum Type {

    ALL(""), ESSENTIAL("B04044"), OPTIONAL("B04045");

    private String value;

    Type(String value) {
        this.value = value;
    }

}
