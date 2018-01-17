package com.sysco.app.dao;

import com.sysco.app.model.Order;
import java.util.List;

public interface OrderDAO {

    void addOrder(Order order);

    void updateOrder(Order order);

    Order findOrderById(long id);

    List<Order> findAllOrders();

}
