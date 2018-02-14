package com.sysco.app.exception;

public class DatabaseException extends SystemException {

    public DatabaseException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
