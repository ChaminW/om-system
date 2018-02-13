package com.sysco.app.annotation;


import com.sysco.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidUntilDateValidator implements ConstraintValidator<CheckValidUntilDate, Long> {

    @Override
    public boolean isValid(Long validUntil,
                           ConstraintValidatorContext cxt) {
        return System.currentTimeMillis() < validUntil;
    }

}
