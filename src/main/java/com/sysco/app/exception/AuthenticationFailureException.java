package com.sysco.app.exception;

public class AuthenticationFailureException extends SystemException {

    public AuthenticationFailureException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
