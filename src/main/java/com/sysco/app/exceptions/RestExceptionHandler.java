package com.sysco.app.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException ex) {
        /*
            Exception resolving code
         */
        return new ResponseEntity<>(ex, ex.getStatus());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
        /*
            Exception resolving code
         */
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}
