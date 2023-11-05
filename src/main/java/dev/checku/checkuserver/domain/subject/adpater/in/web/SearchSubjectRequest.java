package dev.checku.checkuserver.domain.subject.adpater.in.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchSubjectRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String searchQuery;
}
