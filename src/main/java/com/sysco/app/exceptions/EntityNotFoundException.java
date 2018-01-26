package com.sysco.app.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String debugMessage;
    private Object requestedEntity;
    private Class rootClass;

    public EntityNotFoundException(){
        super("Entity not found");
        this.timestamp = LocalDateTime.now();
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public Class getRootClass() {
        return rootClass;
    }

    public void setRootClass(Class rootClass) {
        this.rootClass = rootClass;
    }

    public Object getRequestedEntity() {
        return requestedEntity;
    }

    public void setRequestedEntity(Object requestedEntity) {
        this.requestedEntity = requestedEntity;
    }
}
