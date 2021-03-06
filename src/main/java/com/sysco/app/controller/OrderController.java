package com.sysco.app.controller;

import com.sysco.app.exception.ErrorCode;
import com.sysco.app.exception.ValidUntilValidationException;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import com.sysco.app.service.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/orders")
@Api(value = "orders", tags = "Operations pertaining to orders in Sysco Order Manager")
public class OrderController {

    private final
    OrderService orderService;

    private static final
    Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "Add an order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed")
    })
    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order, Errors errors) throws Exception {
        validateValidUntil(errors);
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }


    @ApiOperation(value = "View orders pageable", produces = "application/json")
    @ApiResponses(value = {
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
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {

        Order order = orderService.readOrder(id);
        return new ResponseEntity<>(order, HttpStatus.FOUND);
    }


    @ApiOperation(value = "Update an order for a given Id", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") String id, @Valid @RequestBody Order order, Errors errors) {
        validateValidUntil(errors);
        Order updatedOrder = orderService.updateOrder(id, order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete an order for a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") String id) {

        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validateValidUntil(Errors errors){
        if (errors.hasErrors()) {
            String errorMessage = ErrorCode.VALID_UNTIL_DATE_FAILURE.getDesc();
            LOGGER.error(errorMessage);
            throw new ValidUntilValidationException("Until date is not valid", ErrorCode.VALID_UNTIL_DATE_FAILURE);
        }
    }

}
