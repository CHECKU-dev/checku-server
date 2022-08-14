package dev.checku.checkuserver.domain.model;

public enum OrderBy {

    CREATE_TIME, EXECUTION_TIME;

    public static OrderBy from(String orderBy) {
        return OrderBy.valueOf(orderBy.toUpperCase());
    }
}
