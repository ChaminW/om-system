package com.sysco.app.exception;

public class SystemException extends RuntimeException {
    private final ErrorCode errorCode;
    private final long timestamp;

    public SystemException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
