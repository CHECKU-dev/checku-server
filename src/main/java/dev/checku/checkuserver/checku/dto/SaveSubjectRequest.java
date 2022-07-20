package dev.checku.checkuserver.checku.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveSubjectRequest {

    private Long userId;

    private String subjectNumber;


}
