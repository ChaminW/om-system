package com.sysco.app.annotation;


import com.sysco.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ValidUntilDateValidator implements ConstraintValidator<CheckValidUntilDate, Long> {

    private final
    RestaurantRepository restaurantRepository;

    @Autowired
    public ValidUntilDateValidator(@Qualifier("restaurantRepository") RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public boolean isValid(Long validUntil,
                           ConstraintValidatorContext cxt) {
//        Long b = 1518459044808L;
        boolean a;
        a = System.currentTimeMillis() < validUntil;
        return a;
    }

}
