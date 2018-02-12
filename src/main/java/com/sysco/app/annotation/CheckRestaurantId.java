package com.sysco.app.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RestaurantIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRestaurantId {
    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
    String message() default "A restuarant should exit to add an order";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
