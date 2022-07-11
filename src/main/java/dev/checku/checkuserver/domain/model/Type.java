package dev.checku.checkuserver.domain.model;

import lombok.Getter;

@Getter
public enum Type {

    ALL(""), ESSENTIAL("B04044"), OPTIONAL("B04045"), OTHER("other");

    private String value;

    Type(String value) {
        this.value = value;
    }

}
