package com.sysco.app.exception;

import java.time.LocalDateTime;

public class SystemException extends RuntimeException {
    private String debugMessage;
    private Class rootClass;
    private String timestamp;

    public SystemException(String debugMessage, Class rootClass) {
        super("System Exception");
        this.debugMessage = debugMessage;
        this.rootClass = rootClass;
        this.timestamp = LocalDateTime.now().toString();
    }

    String getDebugMessage() {
        return debugMessage;
    }

    Class getRootClass() {
        return rootClass;
    }

    String getTimestamp() {
        return timestamp;
    }
}
