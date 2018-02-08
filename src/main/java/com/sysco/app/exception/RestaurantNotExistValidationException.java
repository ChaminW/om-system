package com.sysco.app.exception;

import java.time.LocalDateTime;

public class RestaurantNotExistValidationException extends RuntimeException{
    private String debugMessage;
    private ErrorCode errorCode;
    private Class rootClass;
    private LocalDateTime timestamp;


    public RestaurantNotExistValidationException() {
        super("Restaurant not exist");
        timestamp = LocalDateTime.now();
    }

    public RestaurantNotExistValidationException(String debugMessage, ErrorCode errorCode, Class rootClass) {
        this.debugMessage = debugMessage;
        this.errorCode = errorCode;
        this.rootClass = rootClass;
    }

    public String getDebugMessage() {
        return debugMessage;
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
