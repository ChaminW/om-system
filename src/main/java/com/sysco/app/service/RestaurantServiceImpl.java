package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.OrderRepository;
import com.sysco.app.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Date;


@Component("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    @Qualifier("restaurantRepository")
    @Autowired
    RestaurantRepository restaurantRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    public RestaurantServiceImpl(@Qualifier("restaurantRepository") RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setCreatedAt(Date.from(Instant.now()));
        Restaurant createdRestaurant;

        try {

            createdRestaurant = restaurantRepository.insert(restaurant);
        } catch (MongoException e) {
            String errorMessage = ErrorCode.RESTAURANT_CREATE_FAILURE.getDesc();
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.RESTAURANT_CREATE_FAILURE, RestaurantServiceImpl.class);
        }

        LOGGER.info("Restaurant added", restaurant);

        return createdRestaurant;
    }

    @Override
    public Page<Restaurant> readRestaurantsPageable(int page, int size) {

        // Initiate a page request
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Restaurant> restaurants;

        // Read restaurants
        try {
            restaurants = restaurantRepository.findAll(pageRequest);
        } catch (MongoException e) {
            String errorMessage = ErrorCode.RESTAURANT_READ_FAILURE.getDesc();
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.RESTAURANT_READ_FAILURE, RestaurantServiceImpl.class);
        }

        LOGGER.info("Restaurants retrieved");

        return restaurants;
    }
}