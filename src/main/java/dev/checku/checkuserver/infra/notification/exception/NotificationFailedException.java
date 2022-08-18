package dev.checku.checkuserver.infra.notification.exception;


import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;

public class NotificationFailedException extends BusinessException {

    public NotificationFailedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
