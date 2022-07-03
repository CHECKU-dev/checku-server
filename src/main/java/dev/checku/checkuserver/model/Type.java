package dev.checku.checkuserver.model;

public enum Type {

    ESSENTIAL("B04044"), OPTIONAL("B04045");

    private String value;

    Type(String value) {
        this.value = value;
    }

}
