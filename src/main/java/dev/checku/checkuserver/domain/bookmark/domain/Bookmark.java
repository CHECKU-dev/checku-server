package dev.checku.checkuserver.domain.bookmark.domain;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Bookmark {

    private final Long id;

    private final User user;

    private final SubjectNumber subjectNumber;

    @Builder
    private Bookmark(Long id, User user, SubjectNumber subjectNumber) {
        this.id = id;
        this.user = user;
        this.subjectNumber = subjectNumber;
    }
}
