package dev.checku.checkuserver.domain.notification.dto;

import dev.checku.checkuserver.domain.notification.entity.Notification;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationRegisterReq {

    @NotNull
    private Long userId;

    @NotBlank
    private String subjectNumber;

    @NotBlank
    private String subjectName;

    @NotBlank
    private String professor;

    public Notification toEntity(User user) {
        return Notification.builder()
                .subjectNumber(subjectNumber)
                .subjectName(subjectName)
                .professor(professor)
                .user(user)
                .build();
    }

    @Builder
    public NotificationRegisterReq(Long userId, String subjectNumber, String subjectName, String professor) {
        this.userId = userId;
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
        this.professor = professor;
    }
}
