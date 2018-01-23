package com.sysco.app.controller;

import com.sysco.app.model.Item;
import com.sysco.app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }
}
