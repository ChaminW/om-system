package com.sysco.app.controller;

import com.sysco.app.exceptions.EntityNotFoundException;
import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "items", description = "Operations pertaining to items in Sysco Order Manger")
public class ItemController {

    @Autowired
    ItemService itemService;

    Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ResponseEntity<Item> addItem(@RequestBody Item item) {

        itemService.createItem(item);

        logger.info("Item added", item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View items pageable")
    @GetMapping(value = "/items" )
    public ResponseEntity<Page<Item>> getItems(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> items = itemService.readItemsPageable(pageRequest);

        logger.info("Items retrieved", items);

        return new ResponseEntity<Page<Item>>(items, HttpStatus.OK);
    }

    @ApiOperation(value = "View an item for a given Id")
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getItemById(@PathVariable String id) {

        Item item = itemService.readItemById(id);
        if(item == null){
            logger.info("Empty item");
            throw new EntityNotFoundException(id);
        }

        logger.info("Item retrieved", item);

        return new ResponseEntity<Item>(item, HttpStatus.FOUND);

    }
}
