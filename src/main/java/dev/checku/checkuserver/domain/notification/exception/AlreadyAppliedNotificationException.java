package dev.checku.checkuserver.domain.notification.exception;

import dev.checku.checkuserver.global.exception.BusinessException;
import dev.checku.checkuserver.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyAppliedNotificationException extends BusinessException {

    public AlreadyAppliedNotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
