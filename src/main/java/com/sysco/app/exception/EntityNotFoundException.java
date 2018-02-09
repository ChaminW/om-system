package com.sysco.app.exception;

public class EntityNotFoundException extends SystemException {

    public EntityNotFoundException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
