package com.sysco.app.controller;

import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/items")
@Api(value = "items", description = "Operations pertaining to items in Sysco Order Manger")
public class ItemController {

    @Autowired
    ItemService itemService;

    @ApiOperation(value = "Add an item")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {

        Item createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View items pageable" , produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping
    public ResponseEntity<Page<Item>> getItems(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        Page<Item> items = itemService.readItemsPageable(page, size);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @ApiOperation(value = "View an item for a given Id" , produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Item not found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {

        Item item = itemService.readItemById(id);
        return new ResponseEntity<>(item, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update an item for a given id")
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Item not found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") String id, @RequestBody Item item) {

        Item updatedItem = itemService.updateItem(id, item);
        return new ResponseEntity<>(updatedItem, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete an item for a given id")
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteItem(@Pattern(regexp = "[0-9a-z]*", message = "Id should be of varchar type")
                                                 @PathVariable String id) {

        itemService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
