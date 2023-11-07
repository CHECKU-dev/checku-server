package dev.checku.checkuserver.domain.bookmark.adapter.in.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteBookmarkRequest {

    private Long userId;

    private String subjectNumber;
}
