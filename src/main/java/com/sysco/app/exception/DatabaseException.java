package com.sysco.app.exception;

import java.time.LocalDateTime;

public class DatabaseException extends RuntimeException {
    private String debugMessage;
    private ErrorCode errorCode;
    private Class rootClass;
    private String timestamp;

    private DatabaseException() {
        super("Database Exception");
        timestamp = LocalDateTime.now().toString();
    }

    public DatabaseException(String debugMessage, ErrorCode errorCode, Class rootClass) {
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

    public String getTimestamp() {
        return timestamp;
    }
}
