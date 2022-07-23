package dev.checku.checkuserver.domain.subject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class SaveSubjectRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;


}
