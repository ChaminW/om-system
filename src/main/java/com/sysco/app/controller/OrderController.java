package com.sysco.app.controller;

import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URL;
>>>>>>> be3f54e64a47c057437609138322759d63eec9aa
import java.time.Instant;
import java.util.Date;

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

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));
        orderService.createOrder(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
<<<<<<< HEAD
=======

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrder() {

        List<Order> orders = orderService.readOrder();
        if(orders.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrderById(@PathVariable("id") String id) {

        List<Order> orders = orderService.readOrder(id);
        if(orders.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }




//    @RequestMapping(method = RequestMethod.GET)
//    public List<Order> getOrderList() {
//        System.out.println("In the order list end point");
//        List<Order> orderList = (List<Order>) orderRepository.findAll();
//        Optional<Item> good = goodRepository.findById(orderList.get(0).getItemIdList().get(0));
//        System.out.println(orderList.toString());
//        return orderList;
//    }

>>>>>>> be3f54e64a47c057437609138322759d63eec9aa
}
