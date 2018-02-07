package com.sysco.app.controller;

import com.sysco.app.exceptions.EntityNotFoundException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.exceptions.RestExceptionHandler;
import com.sysco.app.model.Order;
import com.sysco.app.model.Restaurant;
import com.sysco.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.Date;

@Validated
@RestController
@RequestMapping(value = "/orders")
@Api(value = "orders", description = "Operations pertaining to orders in Sysco Order Manager")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value = "Add an order")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed")
    })
    @PostMapping
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order, Errors errors) {

        order.setCreatedDate(Date.from(Instant.now()));
        order.setLastUpdatedAt(Date.from(Instant.now()));
        order.setValidUntil(Date.from(Instant.now()));

        if (errors.hasErrors()) {
            return new ResponseEntity<Order>((Order) null, HttpStatus.BAD_REQUEST);
        }

        orderService.createOrder(order);

        LOGGER.info("Order added", order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View orders pageable", produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 401, message = "Authorization failed"),
    })
    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders = orderService.readOrdersPageable(pageRequest);

        LOGGER.info("Orders retrieved", orders);

        return new ResponseEntity<Page<Order>>(orders, HttpStatus.FOUND);
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
        if(order == null) {
            String errorMessage = "OrderController.getOrderById: Empty order";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ORDER_FOR_THE_ID, OrderController.class);
        }

        LOGGER.info("Order retrieved", order);

        return new ResponseEntity<Order>(order, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update an order for a given Id",produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type") @PathVariable("id") String id, @RequestBody Order order) {

        Order newOrder = orderService.readOrder(id);
        if(newOrder == null) {
            String errorMessage = "OrderController.updateOrder: Empty order";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ITEM_FOR_THE_ID, OrderController.class);
        }
        if(order.getRestaurantId()!= null) {
            newOrder.setRestaurantId(order.getRestaurantId());
        }
        newOrder.setDeliveryAddressId(order.getDeliveryAddressId());
        newOrder.setDeliveryMethod(order.getDeliveryMethod());
        newOrder.setStatus(order.getStatus());

//
//        newOrder.stream()
//
//        Optional.of(newOrder)
//                .map(newOrder::getRestaurantId())
//                .map(Nested::getInner)
//                .map(Inner::getFoo)
//                .ifPresent(System.out::println);

        orderService.updateOrder(newOrder);

        LOGGER.info("Order updated", order);

        return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an order for a given Id", produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 204, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Order> deleteOrder(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type") @PathVariable String id){
        Order currentOrder = orderService.readOrder(id);
        if(currentOrder==null)
        {
            String errorMessage = "OrderController.deleteOrder: Empty order";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ITEM_FOR_THE_ID, OrderController.class);

        }
        orderService.deleteOrder(id);
        return new ResponseEntity<Order>(currentOrder, HttpStatus.NO_CONTENT);
    }
}
