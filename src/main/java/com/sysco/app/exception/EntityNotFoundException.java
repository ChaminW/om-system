package com.sysco.app.exception;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    private ErrorCode errorCode;
    private Class rootClass;
    private String timestamp;

    private EntityNotFoundException(String message){
        super(message);
        timestamp = LocalDateTime.now().toString();
    }

    public EntityNotFoundException(String message, ErrorCode errorCode, Class rootClass) {
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

    public String getTimestamp() {
        return timestamp;
    }
}
