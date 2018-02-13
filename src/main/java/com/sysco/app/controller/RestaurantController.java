package com.sysco.app.controller;

import com.sysco.app.model.Restaurant;
import com.sysco.app.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurants")
@Api(value = "restaurants", tags = "Operations pertaining to restaurants in Sysco Order Manger")
public class RestaurantController {

    private final
    RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @ApiOperation(value = "Add a restaurant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed")
    })
    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {

        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View restaurants pageable", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping
    public ResponseEntity<Page<Restaurant>> getRestaurants(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        Page<Restaurant> restaurants = restaurantService.readRestaurantsPageable(page, size);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}

