package dev.checku.checkuserver.domain.bookmark.adapter.in.web;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoveBookmarkRequest {

    @Getter
    private Long userId;

    private String subjectNumber;

    public SubjectNumber getSubjectNumber() {
        return new SubjectNumber(subjectNumber);
    }
}
