package com.sysco.app.service;

import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    @Qualifier("itemRepository")
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void createItem(Item item) {

    }

    @Override
    public List<Item> readItem() {
        return null;
    }

    @Override
    public List<Item> readItem(Item id) {
        return null;
    }

    @Override
    public List<Item> readItem(long id) {
        return null;
    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(Item id) {

    }

    @Override
    public void delteItem(long id) {

    }
}
