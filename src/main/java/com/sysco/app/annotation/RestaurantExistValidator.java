package com.sysco.app.annotation;


import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.OrderRepository;
import com.sysco.app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RestaurantExistValidator implements ConstraintValidator<CheckRestaurant, String> {

    @Qualifier("restaurantRepository")
    @Autowired
    RestaurantRepository restaurantRepository;

    @Qualifier("orderRepository")
    @Autowired
    OrderRepository orderRepository;

    @Override
    public boolean isValid(String restaurantId,
                           ConstraintValidatorContext cxt) {
        Restaurant restaurant = restaurantRepository.findOrderById(restaurantId);
        return (restaurant != null);
    }

}
