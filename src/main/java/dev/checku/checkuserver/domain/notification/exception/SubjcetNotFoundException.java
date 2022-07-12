package dev.checku.checkuserver.domain.notification.exception;

import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SubjcetNotFoundException extends BusinessException {

    public SubjcetNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
