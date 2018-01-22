package com.sysco.app.service;

public interface OrderService {

    void createOrder(Order order);

    List<Order> readOrder(Order order);

    List<Order> readOrder(long id);

    void updateOrder(Order order);

    void deleteOrder(Order order);

    void delteOrder(long id);
}
