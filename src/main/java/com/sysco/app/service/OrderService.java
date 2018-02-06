package com.sysco.app.service;

import com.sysco.app.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("orderService")
public interface OrderService {

    void createOrder(Order order);

    List<Order> readOrders();

    Page<Order> readOrdersPageable(PageRequest pageRequest);

    Order readOrder(String id);

    void updateOrder(Order order);

    void deleteOrder(String id);
}
