package dev.checku.checkuserver.domain.bookmark.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterBookmarkRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;
}
