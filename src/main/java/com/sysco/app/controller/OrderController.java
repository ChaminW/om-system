package com.sysco.app.controller;

import com.sysco.app.exceptions.OrderNotFoundException;
import com.sysco.app.model.Item;
import com.sysco.app.model.Order;
import com.sysco.app.repository.ItemRepository;
import com.sysco.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    static {
        URL mySource = OrderController.class.getProtectionDomain().getCodeSource().getLocation();
        File rootFolder = new File(mySource.getPath());
        System.setProperty("app.root", rootFolder.getAbsolutePath());
        System.out.println("Init Complete");
    }

    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(value = "/")
    public ResponseEntity<String> rootService() {
        logger.debug("Root service called");
        return new ResponseEntity<String>("Running", HttpStatus.OK);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));
        orderService.createOrder(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping(value = "/order")
    public ResponseEntity<List<Order>> getOrder() {

        List<Order> orders = orderService.readOrder();
        if(orders.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.FOUND);
    }

    @GetMapping(value = "/order/{id}")
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

}
