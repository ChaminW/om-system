package com.sysco.app.controller;


import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Qualifier("OrderRepository")
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getOrderList() {
        System.out.println("In the order list end point");
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        System.out.println(orderList.toString());
        return orderList;
    }

}
