package com.sysco.app.repository;

import com.sysco.app.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderRepository")
public interface OrderRepository {

    void createOrder(Order order);

    List<Order> readOrder(long id);

    List<Order> readOrder(Order order);

    void updateOrder(Order order);

    void delteOrder(long id);

    void deleteOrder(Order order);
}
