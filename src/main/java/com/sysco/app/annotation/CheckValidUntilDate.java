package com.sysco.app.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidUntilDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckValidUntilDate {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
