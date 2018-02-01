package com.sysco.app.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    private String requestedEntity;
    private String debugMessage;
    private Class rootClass;
    private String timestamp;

    private EntityNotFoundException(){
        super("Entity not found");
        timestamp = LocalDateTime.now().toString();
    }

    public EntityNotFoundException(String debugMessage) {
        this();
        this.debugMessage = debugMessage;
    }

    public EntityNotFoundException(String debugMessage, String requestedEntity, Class rootClass) {
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

    public String getRequestedEntity() {
        return requestedEntity;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
