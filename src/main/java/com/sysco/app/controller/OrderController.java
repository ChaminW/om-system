package com.sysco.app.controller;

import com.sysco.app.exceptions.EntityNotFoundException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "/orders")
@Api(value = "orders", description = "Operations pertaining to orders in Sysco Order Manger")
public class OrderController {

    @Autowired
    OrderService orderService;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value = "Add an order")
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {

        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));
        orderService.createOrder(order);

        logger.info("Order added", order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View orders pageable")
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders = orderService.readOrdersPageable(pageRequest);

        logger.info("Orders retrieved", orders);

        return new ResponseEntity<Page<Order>>(orders, HttpStatus.FOUND);
    }

    @ApiOperation(value = "View an order for a given Id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {

        Order order = orderService.readOrder(id);
        if(order == null) {
            logger.info("Empty order");
            throw new EntityNotFoundException("OrderController.getOrderById: Empty order",
                    ErrorCode.NO_ORDER_FOR_THE_ID, OrderController.class);
        }

        logger.info("Order retrieved", order);

        return new ResponseEntity<Order>(order, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update an order for a given Id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable("id") String id){

        Order newOrder = orderService.readOrder(id);
        newOrder.setRestaurantId(order.getRestaurantId());
        newOrder.setDeliveryAddressId(order.getDeliveryAddressId());
        newOrder.setDeliveryMethod(order.getDeliveryMethod());
        newOrder.setStatus(order.getStatus());
        orderService.updateOrder(newOrder);

        logger.info("Order updated", order);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
}
