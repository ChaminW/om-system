package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.controller.OrderController;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ErrorCode;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component("orderService")
public class OrderServiceImpl implements OrderService {

    @Qualifier("orderRepository")
    @Autowired
    OrderRepository orderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public void createOrder(Order order) {

        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));

        try {
            orderRepository.insert(order);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.createOrder: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_CREATE_FAILURE, OrderServiceImpl.class);
        }

        LOGGER.info("Order added", order);
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
    public Page<Order> readOrdersPageable(int page, int size) {

        // Initiate a page request
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders;

        // Read orders
        try {
            orders = orderRepository.findAll(pageRequest);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.readOrdersPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_READ_FAILURE, OrderServiceImpl.class);
        }

        LOGGER.info("Orders retrieved");

        return orders;
    }

    @Override
    public Order readOrder(String id) {

        Order order = new Order();        // Read order
        try {
            order = orderRepository.findOrderById(id);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.readOrder: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_READ_FAILURE, OrderServiceImpl.class);
        }

        // If there is no existing order
        if(order == null) {
            String errorMessage = "OrderServiceImpl.readOrder: Empty order";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ORDER_FOR_THE_ID, OrderController.class);
        }

        LOGGER.info("Order retrieved", order);

        return order;
    }

    @Transactional
    @Override
    public void updateOrder(String id, Order order) {

        // Read order for the given id
        Order newOrder = readOrder(id);

        // Setup parameters
        if(order.getRestaurantId()!= null) {
            newOrder.setRestaurantId(order.getRestaurantId());
        }
        if(order.getDeliveryAddressId()!= null) {
            newOrder.setDeliveryAddressId(order.getDeliveryAddressId());
        }
        if(order.getDeliveryMethod()!= null) {
            newOrder.setDeliveryMethod(order.getDeliveryMethod());
        }
        if(order.getStatus()!= null) {
            newOrder.setStatus(order.getStatus());
        }

        // Update order
        try {
            orderRepository.save(newOrder);
        } catch (MongoException e) {
            String errorMessage = "OrderServiceImpl.updateOrder: Error in updating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_UPDATE_FAILURE, OrderServiceImpl.class);
        }

        LOGGER.info("Order updated", order);
    }

    @Transactional
    @Override
    public void deleteOrderById(String id) {
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
