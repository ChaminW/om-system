package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class SystemException extends RuntimeException {
    protected String debugMessage;
    protected Class rootClass;
    protected LocalDateTime timestamp;

    public SystemException(String debugMessage, Class rootClass) {
        super("System Exception");
        this.debugMessage = debugMessage;
        this.rootClass = rootClass;
        this.timestamp = LocalDateTime.now();
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
