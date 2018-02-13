package com.sysco.app.exception;

import java.time.LocalDateTime;

class SystemException extends RuntimeException {
    final private ErrorCode errorCode;
    final private Class rootClass;
    final private String timestamp;

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
