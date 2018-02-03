package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class DatabaseException extends RuntimeException {
    private String debugMessage;
    private Class rootClass;
    private LocalDateTime timestamp;

    private DatabaseException() {
        super("Database Exception");
        timestamp = LocalDateTime.now();
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
