package com.sysco.app.exception;

public class ValidUntilValidationException extends SystemException {

    public ValidUntilValidationException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
