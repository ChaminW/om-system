package com.sysco.app.dao;

// Ref: http://findnerd.com/list/view/Spring-MVC-and-MongoDB/2240/
// Ref: http://www.baeldung.com/spring-data-mongodb-tutorial

import com.sysco.app.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static AtomicLong counter;
    private static final String COLLECTION_NAME = "order";

    @Override
    public void addOrder(Order order) {
        if(!mongoTemplate.collectionExists(Order.class)){
            mongoTemplate.createCollection(Order.class);
        }

        order.setOrderId(counter.incrementAndGet());
        mongoTemplate.insert(order);
    }

    @Override
    public void updateOrder(Order order) {
        mongoTemplate.save(order);
    }

    @Override
    public Order findOrderById(long id) {
        return null;
    }

    @Override
    public List<Order> findAllOrders() {
        return null;
    }
}
