package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.exception.SystemException;
import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    private final
    RestaurantRepository restaurantRepository;

    private static final
    Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    public RestaurantServiceImpl(@Qualifier("restaurantRepository") RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setCreatedAt(System.currentTimeMillis());
        Restaurant createdRestaurant;

        try {
            createdRestaurant = restaurantRepository.insert(restaurant);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.RESTAURANT_CREATE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.RESTAURANT_CREATE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Restaurant created", createdRestaurant.getId());
        return createdRestaurant;
    }

    @Override
    public Page<Restaurant> readRestaurantsPageable(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Restaurant> restaurants;

        try {
            restaurants = restaurantRepository.findAll(pageRequest);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.RESTAURANT_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.RESTAURANT_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Restaurants retrieved");
        return restaurants;
    }
}