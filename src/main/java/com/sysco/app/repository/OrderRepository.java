package com.sysco.app.repository;

import com.sysco.app.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("OrderRepository")
public interface OrderRepository extends MongoRepository<Order, String> {


}
