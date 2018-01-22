package com.sysco.app.controller;


import com.sysco.app.model.Good;
import com.sysco.app.model.Order;
import com.sysco.app.repository.GoodRepository;
import com.sysco.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    @Qualifier("orderRepository")
    OrderRepository orderRepository;

    @Autowired
    @Qualifier("goodRepository")
    GoodRepository goodRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getOrderList() {
        System.out.println("In the order list end point");
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        Optional<Good> good = goodRepository.findById(orderList.get(0).getGoodsIdList().get(0));
        System.out.println(orderList.toString());
        return orderList;
    }

}
