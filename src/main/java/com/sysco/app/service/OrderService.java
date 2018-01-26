package com.sysco.app.service;

import com.sysco.app.model.Order;
import java.util.List;

public interface OrderService {

    void createOrder(Order order);

    List<Order> readOrder();

    List<Order> readOrder(Order order);

    Order readOrder(String id);

    void updateOrder(Order order);

    void deleteOrder(String id);
}
