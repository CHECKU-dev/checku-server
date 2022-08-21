package dev.checku.checkuserver.domain.notification.exception;

import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SubjectHasVacancyException extends BusinessException {

    public SubjectHasVacancyException(ErrorCode errorCode) {
        super(errorCode);
    }

}
