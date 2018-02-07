package com.sysco.app.exception;

public enum ErrorCode {

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


    ;

    private String desc;
    private int code;

    private ErrorCode(int code, String desc) {
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
