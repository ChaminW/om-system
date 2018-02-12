package com.sysco.app.exception;

public class ValidationFailureException extends SystemException {

    public ValidationFailureException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
