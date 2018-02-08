package com.sysco.app.exception;

import java.time.LocalDateTime;

public class SystemException extends RuntimeException {
    protected String debugMessage;
    protected Class rootClass;
    protected String timestamp;

    public SystemException(String debugMessage, Class rootClass) {
        super("System Exception");
        this.debugMessage = debugMessage;
        this.rootClass = rootClass;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
