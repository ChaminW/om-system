package com.sysco.app.service;

import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.sysco.app.exceptions.DatabaseException;
import com.sysco.app.exceptions.SystemException;
import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    @Qualifier("itemRepository")
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void createItem(Item item) {
        itemRepository.insert(item);
    }

    @Override
    public List<Item> readItems() {
        List<Item> items = null;
        try {
            items = itemRepository.findAll();
        } catch (MongoException e) {
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Database Error",
                    ItemServiceImpl.class);
        }
        return items;
    }

    @Override
    public Page<Item> readItemsPageable(PageRequest pageRequest) {
        Page<Item> items = null;
        try {
            items = itemRepository.findAll(pageRequest);
        } catch (MongoException e) {
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Database Error",
                    ItemServiceImpl.class);
        }
        return items;
    }

    @Override
    public Item readItemById(String id) {
        return itemRepository.findItemById(id);
    }

    @Override
    public void updateItem(Item item, String id) {

    }

    @Override
    public void deleteItem(String id) {

    }
}
