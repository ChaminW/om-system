package com.sysco.app.exception;

public enum ErrorCode {

    // System Exceptions
    INTERNAL_SERVER_ERROR(0, "Internal Server Error"),

    // Entity Not Found Exceptions
    NO_ITEM_FOR_THE_ID(10, "Item not found"),
    NO_ORDER_FOR_THE_ID(11, "Order not found"),

    // Database Exceptions
    ITEM_CREATE_FAILURE(20, "Cannot create item"),
    ITEM_READ_FAILURE(21, "Cannot read item"),
    ITEM_UPDATE_FAILURE(22, "Cannot update item"),
    ITEM_DELETE_FAILURE(23, "Cannot delete item"),
    ORDER_CREATE_FAILURE(24, "Cannot create order"),
    ORDER_READ_FAILURE(25, "Cannot read order"),
    ORDER_UPDATE_FAILURE(26, "Cannot update order"),
    ORDER_DELETE_FAILURE(27, "Cannot delete order"),
    RESTAURANT_CREATE_FAILURE(28, "Cannot create restaurant"),
    RESTAURANT_READ_FAILURE(29, "Cannot read restaurant"),

    //Validation Exceptions
    ORDER_VALIDATION_FAILURE_RESTAURANT_NOT_EXIST(30, "Restaurant not exists"),
    ORDER_VALIDATION_FAILURE_ITEM_NOT_EXIST(31, "Item(s) not exist"),
    ORDER_ID_VALIDATION_FAILURE(32, "Invalid pattern for order id"),
    ITEM_ID_VALIDATION_FAILURE(33, "Invalid pattern for item item id"),

    //Security Exceptions
    AUTHENTICATION_FAILURE(40, "Authentication failure"),
    MISSING_AUTHENTICATION_KEY(41, "Authentication key is missing");

    private final String desc;
    private final int code;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
