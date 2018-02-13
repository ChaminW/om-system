package com.sysco.app.exception;

import java.time.LocalDateTime;

public class SystemException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Class rootClass;
    private final String timestamp;

    public SystemException(String message, ErrorCode errorCode, Class rootClass) {
        super(message);
        this.errorCode = errorCode;
        this.rootClass = rootClass;
        this.timestamp = LocalDateTime.now().toString();
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
