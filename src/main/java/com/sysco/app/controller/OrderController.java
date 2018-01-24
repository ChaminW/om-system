package com.sysco.app.controller;

import com.sysco.app.exceptions.OrderNotFoundException;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(value = "order", description = "Order API")
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
        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrderById(@PathVariable("id") String id) {
        List<Order> orders = orderService.readOrder(id);
        if(orders.isEmpty()) {
            throw new OrderNotFoundException(id);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }

}
