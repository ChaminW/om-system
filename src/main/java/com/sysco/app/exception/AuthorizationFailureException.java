package com.sysco.app.exception;

public class AuthorizationFailureException extends SystemException {

    public AuthorizationFailureException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
