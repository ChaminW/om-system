package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    private Object requestedEntity;
    private String debugMessage;
    private Class rootClass;
    private LocalDateTime timestamp;

    private EntityNotFoundException(){
        super("Entity not found");
        timestamp = LocalDateTime.now();
    }

    public EntityNotFoundException(String debugMessage) {
        this();
        this.debugMessage = debugMessage;
    }

    public EntityNotFoundException(String debugMessage, Object requestedEntity, Class rootClass) {
        this();
        this.debugMessage = debugMessage;
        this.requestedEntity = requestedEntity;
        this.rootClass = rootClass;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public Object getRequestedEntity() {
        return requestedEntity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
