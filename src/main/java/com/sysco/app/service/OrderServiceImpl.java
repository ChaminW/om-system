package com.sysco.app.service;

import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Qualifier("orderRepository")
    @Autowired
    OrderRepository orderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public void createOrder(Order order) {
        orderRepository.insert(order);
    }

    @Override
    public List<Order> readOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> readOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> readOrder(long id) {
        return null;
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {

    }

    @Transactional
    @Override
    public void deleteOrder(Order order) {

    }

    @Transactional
    @Override
    public void deleteOrder(long id) {

    }
}
