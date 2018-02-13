package com.sysco.app.exception;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice


public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE = "message";
    private static final String ERROR_CODE = "errorCode";
    private static final String ROOT_CLASS = "rootClass";
    private static final String TIMESTAMP = "timestamp";

    private final
    MessageSource messageSource;

    /*
        User Defined Exception Handlers
     */
    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({SystemException.class})
    protected ResponseEntity<Object> handleSystemException(SystemException ex) {

        return sendErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {

        return sendErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DatabaseException.class})
    protected ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {

        return sendErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ValidUntilValidationException.class})
    public ResponseEntity<Object> handleRestaurantNotExistValidationException(ValidUntilValidationException ex) {

        return sendErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationFailureException.class})
    public ResponseEntity<Object> handleAuthorizationFailureException(AuthenticationFailureException ex) {

        return sendErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ValidationFailureException.class})
    public ResponseEntity<Object> handleValidationFailureException(ValidationFailureException ex) {

        return sendErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> sendErrorResponse(SystemException ex, HttpStatus httpStatus) {

        Document error = new Document();

        error.put(MESSAGE, messageSource.getMessage(String.valueOf(ex.getErrorCode().getCode()), null,
                LocaleContextHolder.getLocale()));
        error.put(ERROR_CODE, ex.getErrorCode().getCode());
        error.put(ROOT_CLASS, ex.getRootClass());
        error.put(TIMESTAMP, ex.getTimestamp());

        return new ResponseEntity<Object>(error, httpStatus);
    }

    /*
        Standard Exception Handlers
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {

        Document error = new Document();
        error.put(MESSAGE, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {

        Document error = new Document();
        error.put(MESSAGE, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
