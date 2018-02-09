package com.sysco.app.exception;

public class RestaurantNotExistValidationException extends SystemException{

    public RestaurantNotExistValidationException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
