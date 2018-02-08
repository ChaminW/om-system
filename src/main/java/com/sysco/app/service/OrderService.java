package com.sysco.app.service;

import com.sysco.app.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.validation.Errors;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order createValidatedOrder(Order order, Errors errors);

    List<Order> readOrders();

    Page<Order> readOrdersPageable(int page, int size);

    Order readOrder(String id);

    Order updateOrder(String id, Order order);

    void deleteOrderById(String id);
}
