package com.sysco.app.repository;

import com.sysco.app.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "orderRepository")
public interface OrderRepository extends MongoRepository<Order, String>, PagingAndSortingRepository<Order, String> {
    Order findOrderById(String id);
}
