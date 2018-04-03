package com.sysco.app.annotation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidUntilDateValidator implements ConstraintValidator<CheckValidUntilDate, Long> {

    @Override
    public boolean isValid(Long validUntil,
                           ConstraintValidatorContext cxt) {
        return validUntil == null || System.currentTimeMillis() < validUntil;
    }

}
