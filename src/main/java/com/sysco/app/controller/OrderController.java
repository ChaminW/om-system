package com.sysco.app.controller;

import com.sysco.app.exceptions.OrderNotFoundException;
import com.sysco.app.model.Order;
import com.sysco.app.repository.ItemRepository;
import com.sysco.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.net.URL;
import java.util.List;

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
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> rootService() {
        logger.debug("Root service called");
        return new ResponseEntity<String>("Running", HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {

        orderService.createOrder(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrder() {

        List orders = orderService.readOrder();

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
