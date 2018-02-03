package com.sysco.app.exceptions;

import org.bson.Document;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SystemException.class})
    protected ResponseEntity<Object> handleException(SystemException ex) {

        Document error = new Document();

        error.put("message", ex.getMessage());
        error.put("debug", ex.getDebugMessage());
        error.put("rootClass", ex.getRootClass());
        error.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        /*
            Exception resolving code
         */

        Document error = new Document();

        error.put("message", ex.toString());
        error.put("errorCode", ex.getErrorCode());
        error.put("debug",ex.getDebugMessage());
        error.put("rootClass", ex.getRootClass());
        error.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<Object>(error , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DatabaseException.class})
    protected ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {
        /*
            Exception resolving code
         */

        Document error = new Document();

        error.put("message", ex.toString());
        error.put("errorCode", ex.getErrorCode());
        error.put("debug", ex.getDebugMessage());
        error.put("rootClass", ex.getRootClass());
        error.put("timestamp", ex.getTimestamp());

        return new ResponseEntity<Object>(error , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
