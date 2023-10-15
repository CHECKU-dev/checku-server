package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelNotificationRequest {

    @Getter
    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;

    @Builder
    public CancelNotificationRequest(Long userId, String subjectNumber) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
    }

    public SubjectNumber getSubjectNumber() {
        return new SubjectNumber(subjectNumber);
    }
}
