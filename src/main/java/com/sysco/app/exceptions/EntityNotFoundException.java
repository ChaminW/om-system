package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    private String debugMessage;
    private ErrorCode errorCode;
    private Class rootClass;
    private LocalDateTime timestamp;

    private EntityNotFoundException(){
        super("Entity not found");
        timestamp = LocalDateTime.now();
    }

    public EntityNotFoundException(String debugMessage) {
        this();
        this.debugMessage = debugMessage;
    }

    public EntityNotFoundException(String debugMessage,ErrorCode errorCode, Class rootClass) {
        this();
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
