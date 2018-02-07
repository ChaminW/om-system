package com.sysco.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ItemsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemsConstraint {
    String message() default "Invalid item";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
