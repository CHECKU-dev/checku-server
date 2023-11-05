package dev.checku.checkuserver.domain.subject.adpater.in.web;

import dev.checku.checkuserver.domain.subject.domain.Department;
import dev.checku.checkuserver.domain.subject.domain.Grade;
import dev.checku.checkuserver.domain.subject.domain.Type;
import dev.checku.checkuserver.global.advice.Enum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAllSubjectsRequest {

    private Long userId;

    @Enum(enumClass = Department.class, message = "잘못된 Enum 값 입니다.")
    private String department;

    @Enum(enumClass = Grade.class, message = "잘못된 Enum 값 입니다.", isNullable = true)
    private String grade;

    @Enum(enumClass = Type.class, message = "잘못된 Enum 값 입니다.", isNullable = true)
    private String type;

    private Boolean vacancy = Boolean.FALSE;
}
