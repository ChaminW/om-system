package com.sysco.app.validator;

public class OrderValidator {

    private OrderValidator() {
    }

    public static boolean isValidId(String id) {
        return id.matches("[0-9a-z]*");
    }
}
