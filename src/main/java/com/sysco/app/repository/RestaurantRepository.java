package com.sysco.app.repository;

import com.sysco.app.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "restaurantRepository")
public interface RestaurantRepository extends MongoRepository<Restaurant, String>, PagingAndSortingRepository<Restaurant, String> {
    Restaurant findOrderById (String id);
}
