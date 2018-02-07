package com.sysco.app.controller;

import com.sysco.app.exceptions.EntityNotFoundException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/items")
@Api(value = "items", description = "Operations pertaining to items in Sysco Order Manger")
public class ItemController {

    @Autowired
    ItemService itemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @ApiOperation(value = "Add an item")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
    })
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {

        itemService.createItem(item);

        LOGGER.info("Item added", item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }


    @ApiOperation(value = "View items pageable" , produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 401, message = "Authorization failed"),
    })
    @GetMapping
    public ResponseEntity<Page<Item>> getItems(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> items = itemService.readItemsPageable(pageRequest);

        LOGGER.info("Items retrieved", items);

        return new ResponseEntity<Page<Item>>(items, HttpStatus.OK);
    }

    @ApiOperation(value = "View an item for a given Id" , produces = "application/json")
    @ApiResponses( value = {
            @ApiResponse(code = 302, message = "Successful"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Item not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {

        Item item = itemService.readItemById(id);

        if(item == null){
            String errorMessage = "ItemController.getItemById: Empty item";
            LOGGER.info(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ITEM_FOR_THE_ID, ItemController.class);
        }

        LOGGER.info("Item retrieved", item);

        return new ResponseEntity<Item>(item, HttpStatus.FOUND);
    }

    @ApiOperation(value = "Update an item for a given id")
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "Authorization failed"),
            @ApiResponse(code = 404, message = "Item not found")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") String id, @RequestBody Item item) {

        Item newItem = itemService.readItemById(id);

        if(newItem == null) {
            String errorMessage = "ItemController.updateItem: Empty item";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ITEM_FOR_THE_ID, ItemController.class);
        }

        newItem.setName(item.getName());
        newItem.setType(item.getType());
        newItem.setPricePerItem(item.getPricePerItem());
        newItem.setTotalQuantity(item.getTotalQuantity());
        newItem.setDescription(item.getDescription());
        itemService.updateItem(newItem);

        LOGGER.info("Item updated");

        return new ResponseEntity<Item>(newItem, HttpStatus.OK);
    }
}
