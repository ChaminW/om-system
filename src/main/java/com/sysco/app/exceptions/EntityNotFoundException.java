package com.sysco.app.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public EntityNotFoundException(String message) {
        super("Item not found");
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
