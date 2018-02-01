package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class DatabaseException extends RuntimeException {
    private String debugMessage;
    private Class rootClass;
    private String timestamp;

    private DatabaseException() {
        super("Database Exception");
        timestamp = LocalDateTime.now().toString();
    }

    public DatabaseException(String debugMessage, Class rootClass) {
        this();
        this.debugMessage = debugMessage;
        this.rootClass = rootClass;
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
