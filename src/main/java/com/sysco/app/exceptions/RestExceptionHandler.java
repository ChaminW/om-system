package com.sysco.app.exceptions;

import org.bson.Document;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        /*
            Exception resolving code
         */
        Document error = new Document();

        error.put("message", ex.toString());
        error.put("requestedEntity", ex.getRequestedEntity());
        error.put("debug",ex.getDebugMessage());
        error.put("rootClass", ex.getRootClass());
        error.put("timestamp", ex.getTimestamp().toString());

        return new ResponseEntity<Object>(error , HttpStatus.NOT_FOUND);
    }



}
