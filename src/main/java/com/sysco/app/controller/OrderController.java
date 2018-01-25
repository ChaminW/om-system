package com.sysco.app.controller;

import com.sysco.app.exceptions.OrderNotFoundException;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> rootService() {
        logger.debug("Root service called");
        return new ResponseEntity<String>("Running", HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrder() {

        List<Order> orders = orderService.readOrder();
        if(orders.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {

        Order order = orderService.readOrder(id);
        if(order == null) {
            throw new OrderNotFoundException();
        }

        return new ResponseEntity<Order>(order, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable("id") String id){
        Order newOrder = orderService.readOrder(id);
        newOrder.setRestaurantId(order.getRestaurantId());
        newOrder.setDeliveryAddressId(order.getDeliveryAddressId());
        newOrder.setDeliveryMethod(order.getDeliveryMethod());
        newOrder.setStatus(order.getStatus());
        orderService.updateOrder(order);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}

