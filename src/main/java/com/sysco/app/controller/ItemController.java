package com.sysco.app.controller;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import com.sysco.app.exceptions.ItemNotFoundException;
import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;


    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = itemService.readItems();
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItemsById(@PathVariable String id) {
        List<Item> items = itemService.readItemsById(id);
        if(items.isEmpty()){
            throw new ItemNotFoundException(id);
        } else {
            return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
        }
    }
}
