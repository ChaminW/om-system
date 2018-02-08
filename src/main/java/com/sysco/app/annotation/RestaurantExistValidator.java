package com.sysco.app.annotation;


import com.sysco.app.model.Order;
import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.OrderRepository;
import com.sysco.app.repository.RestaurantRepository;
import com.sysco.app.service.OrderService;
import com.sysco.app.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

@Component
public class RestaurantExistValidator implements ConstraintValidator<CheckRestaurant, String> {

    @Qualifier("restaurantRepository")
    @Autowired
    RestaurantRepository restaurantRepository;

    @Qualifier("orderRepository")
    @Autowired
    OrderRepository orderRepository;

    @Override
    public void initialize(CheckRestaurant contactNumber) {
    }

    @Override
    public boolean isValid(String restaurantId,
                           ConstraintValidatorContext cxt) {
        Restaurant restaurant = restaurantRepository.findOrderById(restaurantId);
        return (restaurant != null);
    }

}
