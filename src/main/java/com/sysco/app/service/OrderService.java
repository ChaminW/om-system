package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ValidationFailureException;
import com.sysco.app.model.Order;
import org.springframework.data.domain.Page;
import java.util.List;

public interface OrderService {

    /**
     *
     * @param order new Order object pass through the end point
     * @return newly created order object returns from the database
     * @throws MongoException raise if database connection failure
     * @throws ValidationFailureException raise if a Restaurant does not exists for the given restaurant id
     */
    Order createOrder(Order order) throws Exception;

    /**
     *
     * @return all the existing Order objects will be returns as a list
     * @throws MongoException raise if database connection failure
     */
    List<Order> readOrders();

    /**
     *
     * @param page
     * @param size
     * @return
     */
    Page<Order> readOrdersPageable(int page, int size);

    /**
     *
     * @param id Id of the Order object that we need to retrieve
     * @return Order object retrieved from the database for the given Order Id
     * @throws MongoException raise if database connection failure
     * @throws EntityNotFoundException raise if no Order object exists for the given Id
     */
    Order readOrder(String id);

    /**
     *
     * @param id Id of the Order object that we need to update
     * @param order Order object with values need to be updated
     * @return updated Order object
     * @throws MongoException raise if database connection failure
     * @throws EntityNotFoundException raise if no Order object exists for the given Id
     */
    Order updateOrder(String id, Order order);

    /**
     *
     * @param id Id of the Order object that we need to delete
     * @throws MongoException raise if database connection failure
     */
    void deleteOrderById(String id);
}
