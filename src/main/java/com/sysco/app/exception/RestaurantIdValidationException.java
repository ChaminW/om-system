package com.sysco.app.exception;

public class RestaurantIdValidationException extends SystemException{

    public RestaurantIdValidationException(String message, ErrorCode errorCode, Class rootClass) {
        super(message, errorCode, rootClass);
    }
}
