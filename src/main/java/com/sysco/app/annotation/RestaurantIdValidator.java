package com.sysco.app.annotation;


import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

@Component
public class RestaurantIdValidator implements ConstraintValidator<CheckRestaurantId, String> {

    private final
    RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantIdValidator(@Qualifier("restaurantRepository") RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public boolean isValid(String restaurantId,
                           ConstraintValidatorContext cxt) {
        Restaurant restaurant = restaurantRepository.findOrderById(restaurantId);
        return (!StringUtils.isBlank(restaurantId)  && restaurant != null);
    }

}
