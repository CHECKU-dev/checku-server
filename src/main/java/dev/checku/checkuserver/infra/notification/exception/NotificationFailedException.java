package dev.checku.checkuserver.infra.notification.exception;


import dev.checku.checkuserver.global.exception.BusinessException;
import dev.checku.checkuserver.global.exception.ErrorCode;
import org.apache.tomcat.jni.Error;

public class NotificationFailedException extends BusinessException {

    public NotificationFailedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
