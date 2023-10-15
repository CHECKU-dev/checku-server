package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterNotificationRequest {

    @NotNull
    @Getter
    private Long userId;

    @NotBlank
    private String subjectNumber;

    @NotBlank
    @Getter
    private String subjectName;

    @NotBlank
    @Getter
    private String professor;

    @Builder
    private RegisterNotificationRequest(Long userId, String subjectNumber, String subjectName, String professor) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.professor = professor;
    }

    public SubjectNumber getSubjectNumber() {
        return new SubjectNumber(subjectNumber);
    }

    public Notification toEntity(User user) {
        return Notification.builder()
                .subjectNumber(new SubjectNumber(subjectNumber))
                .subjectName(subjectName)
                .professor(professor)
                .user(user)
                .build();
    }
}
