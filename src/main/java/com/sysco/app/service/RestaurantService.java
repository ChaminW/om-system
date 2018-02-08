package com.sysco.app.service;

import com.sysco.app.model.Restaurant;
import org.springframework.data.domain.Page;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant order);
    Page<Restaurant> readRestaurantsPageable(int page, int size);
}
