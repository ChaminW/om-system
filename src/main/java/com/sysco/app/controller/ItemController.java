package com.sysco.app.controller;

import com.sun.org.apache.regexp.internal.RESyntaxException;
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

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems(@RequestParam("name") String name) {
        List<Item> items = itemService.readItem(name);
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
        }
    }
}
