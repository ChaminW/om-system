package com.sysco.app.controller;

import com.sysco.app.exceptions.EntityNotFoundException;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@Api(value = "orders", description = "Operations pertaining to orders in Sysco Order Manger")
public class OrderController {

    @Autowired
    OrderService orderService;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> rootService() {

        logger.info("Root service called {}");

        return new ResponseEntity<String>("Running", HttpStatus.OK);
    }

    @RequestMapping(value = "/orderw", method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {

        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));
        orderService.createOrder(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orderw", method = RequestMethod.GET)
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders = orderService.readOrdersPageable(pageRequest);

        return new ResponseEntity<Page<Order>>(orders, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {

        Order order = orderService.readOrder(id);
        if(order == null) {
            throw new EntityNotFoundException(id);
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
        orderService.updateOrder(newOrder);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}
