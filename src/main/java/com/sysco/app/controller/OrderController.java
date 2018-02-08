package com.sysco.app.controller;


import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping(value = "/orders")
@Api(value = "orders", description = "Operations pertaining to orders in Sysco Order Manager")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value = "Add an order")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed")
    })
    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order, Errors errors) {

        if(errors.hasErrors()){
            System.out.println("has errors");
            return new ResponseEntity<>((Order) null, HttpStatus.BAD_REQUEST);
        }
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View orders pageable", produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 401, message = "Authorization failed"),
    })
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        Page<Order> orders = orderService.readOrdersPageable(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @ApiOperation(value = "View an order for a given Id", produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type")
                                              @PathVariable("id") String id) {
        Order order = orderService.readOrder(id);
        return new ResponseEntity<>(order, HttpStatus.FOUND);
    }


    @ApiOperation(value = "Update an order for a given Id",produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type")
                                             @PathVariable("id") String id, @RequestBody Order order) {

        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Delete an order for a given Id")
    @ApiResponses( value = {
            @ApiResponse(code = 204, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteOrder(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type")
                                                  @PathVariable String id){

        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
