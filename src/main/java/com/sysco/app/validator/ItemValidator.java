package com.sysco.app.validator;

public class ItemValidator {

    private ItemValidator() {
    }

    public static boolean isValidId(String id) {
        return id.matches("[0-9a-z]*");
    }

}
