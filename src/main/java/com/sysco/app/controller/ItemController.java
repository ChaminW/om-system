package com.sysco.app.controller;

import com.sysco.app.exceptions.ItemNotFoundException;
import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(value = "items", description = "Item API")
public class ItemController {

    @Autowired
    ItemService itemService;


    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemService.readItems();
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Item item = itemService.readItemsById(id);
        if(item == null){
            throw new ItemNotFoundException(id);
        } else {
            return new ResponseEntity<Item>(item, HttpStatus.FOUND);
        }
    }
}
