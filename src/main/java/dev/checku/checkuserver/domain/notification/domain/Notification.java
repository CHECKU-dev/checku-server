package dev.checku.checkuserver.domain.notification.domain;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification {

    private final Long id;

    private final Long userId;

    private final SubjectNumber subjectNumber;

    @Builder
    private Notification(Long id, Long userId, SubjectNumber subjectNumber) {
        this.id = id;
        this.userId = userId;
        this.subjectNumber = subjectNumber;
    }
}
