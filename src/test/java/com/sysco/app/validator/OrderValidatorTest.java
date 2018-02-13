package com.sysco.app.validator;

import org.junit.Assert;
import org.junit.Test;

public class OrderValidatorTest {

    @Test
    public void isValidId() {
        Assert.assertTrue(ItemValidator.isValidId("5a5f72d2062cb49fbcd43ad9"));
    }
}