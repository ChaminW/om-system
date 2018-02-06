package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exceptions.DatabaseException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        try {
            orderRepository.insert(order);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.createOrder: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_CREATE_FAILURE, OrderServiceImpl.class);
        }
    }

    @Override
    public List<Order> readOrders() {
        try {
            return orderRepository.findAll();
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.readOrder: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_READ_FAILURE, OrderServiceImpl.class);
        }
    }

    @Override
    public Page<Order> readOrdersPageable(PageRequest pageRequest) {
        try {
            return orderRepository.findAll(pageRequest);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.readOrdersPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_READ_FAILURE, OrderServiceImpl.class);
        }
    }

    @Override
    public Order readOrder(String id) {
        try {
            return orderRepository.findOrderById(id);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.readOrder: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_READ_FAILURE, OrderServiceImpl.class);
        }
            }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.updateOrder: Error in updating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_UPDATE_FAILURE, OrderServiceImpl.class);
        }
    }

    @Transactional
    @Override
    public void deleteOrder(String id) {
        try {
            orderRepository.deleteById(id);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.deleteOrder: Error in deleting";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_DELETE_FAILURE, OrderServiceImpl.class);
        }
    }
}
