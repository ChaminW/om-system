package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.controller.OrderController;
import com.sysco.app.exception.*;
import com.sysco.app.model.Order;
import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.OrderRepository;
import com.sysco.app.repository.RestaurantRepository;
import com.sysco.app.validator.OrderValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component("orderService")
public class OrderServiceImpl implements OrderService {

    private final
    OrderRepository orderRepository;

    private final
    RestaurantRepository restaurantRepository;

    private static final
    Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(@Qualifier("orderRepository") OrderRepository orderRepository, @Qualifier("restaurantRepository") RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public Order createOrder(Order order){

        order.setId(null);
        String restaurantId = order.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findOrderById(restaurantId);
        if(StringUtils.isBlank(restaurantId) || restaurant == null){
            LOGGER.error(ErrorCode.NO_RESTAURANT_EXIST_FAILURE.getDesc());
            throw new ValidationFailureException(this.getClass().getName(), ErrorCode.NO_RESTAURANT_EXIST_FAILURE);
        }

        order.setCreatedDate(System.currentTimeMillis());
        order.setLastUpdatedAt(System.currentTimeMillis());
        Order createdOrder;
        try {
            createdOrder = orderRepository.insert(order);
        } catch (MongoException e) {
<<<<<<< HEAD
            LOGGER.error(ErrorCode.ORDER_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_CREATE_FAILURE);
=======
            String errorMessage = ErrorCode.ORDER_CREATE_FAILURE.getDesc();
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ORDER_CREATE_FAILURE, OrderServiceImpl.class);
>>>>>>> e774ba92c963e7f225688940f108bb090cb0d990
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Order created ", createdOrder.getId());
        return createdOrder;
    }

    @Override
    public List<Order> readOrders() {

        List<Order> orders;
        try {
            orders = orderRepository.findAll();
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ORDER_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Orders retrieved");
        return orders;
    }

    @Override
    public Page<Order> readOrdersPageable(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders;

        try {
            orders = orderRepository.findAll(pageRequest);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ORDER_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Orders retrieved");
        return orders;
    }

    @Override
    public Order readOrder(String id) {
        validateId(id);
        Order order;
        try {
            order = orderRepository.findOrderById(id);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ORDER_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if (order == null) {
            LOGGER.error(ErrorCode.NO_ORDER_FOR_THE_ID.getDesc());
            throw new EntityNotFoundException(this.getClass().getName(), ErrorCode.NO_ORDER_FOR_THE_ID);
        }

        LOGGER.info("Order retrieved ", order.getId());
        return order;
    }

    @Transactional
    @Override
    public Order updateOrder(String id, Order order) {
        validateId(id);
        Order dbOrder = readOrder(id);
        dbOrder.setLastUpdatedAt(System.currentTimeMillis());

        if (order.getDeliveryAddressId() != null) {
            dbOrder.setDeliveryAddressId(order.getDeliveryAddressId());
        }
        if (order.getDeliveryMethod() != null) {
            dbOrder.setDeliveryMethod(order.getDeliveryMethod());
        }
        if (order.getStatus() != null) {
            dbOrder.setStatus(order.getStatus());
        }
        if (order.getValidUntil() != null) {
            dbOrder.setValidUntil(order.getValidUntil());
        }

        try {
            orderRepository.save(dbOrder);
        } catch (MongoException e) {
            LOGGER.error (ErrorCode.ORDER_UPDATE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_UPDATE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Order updated ", dbOrder.getId());
        return dbOrder;
    }

    @Transactional
    @Override
    public void deleteOrderById(String id) {
        validateId(id);
        try {
            orderRepository.deleteById(id);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ORDER_DELETE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ORDER_DELETE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Order deleted ", id);
    }

    private void validateId(String id){

        if (!OrderValidator.isValidId(id)) {
            LOGGER.error(ErrorCode.ORDER_ID_VALIDATION_FAILURE.getDesc());
            throw new ValidationFailureException(this.getClass().getName(), ErrorCode.ORDER_ID_VALIDATION_FAILURE);
        }
    }
}
