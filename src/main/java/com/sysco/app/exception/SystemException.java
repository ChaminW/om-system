package com.sysco.app.exception;

public class SystemException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Class rootClass;
    private final long timestamp;

    public SystemException(String message, ErrorCode errorCode, Class rootClass) {
        super(message);
        this.errorCode = errorCode;
        this.rootClass = rootClass;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
