package com.sysco.app.controller;

import com.sysco.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Qualifier("OrderRepository")
    @Autowired
    OrderRepository orderRepository;



}
