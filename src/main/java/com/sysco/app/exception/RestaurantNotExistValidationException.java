package com.sysco.app.exception;

import java.time.LocalDateTime;

public class RestaurantNotExistValidationException extends RuntimeException{
    private ErrorCode errorCode;
    private Class rootClass;
    private LocalDateTime timestamp;


    public RestaurantNotExistValidationException(String message) {
        super(message);
        timestamp = LocalDateTime.now();
    }

    public RestaurantNotExistValidationException(String message, ErrorCode errorCode, Class rootClass) {
        this(message);
        this.errorCode = errorCode;
        this.rootClass = rootClass;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
