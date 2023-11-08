package dev.checku.checkuserver.domain.session.adapter.out.persistence;

import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;

public class PortalSessionRedisEntityNotFoundException extends EntityNotFoundException {

    public PortalSessionRedisEntityNotFoundException() {
        super(ErrorCode.SESSION_NOT_FOUND);
    }
}
