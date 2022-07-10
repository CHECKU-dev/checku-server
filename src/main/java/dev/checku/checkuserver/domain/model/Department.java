package dev.checku.checkuserver.domain.model;

import lombok.Getter;

@Getter
public enum Department {

    공과대학_컴퓨터공학부("127428");
    
    private String value;

    Department(String value) {
        this.value = value;
    }


}
