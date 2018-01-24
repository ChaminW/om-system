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
        itemRepository.insert(item);
    }

    @Override
    public List<Item> readItems() {
        return itemRepository.findAll();
    }

<<<<<<< HEAD
    @Override
    public List<Item> readItemsById(String id) {
        return itemRepository.findItemsById(id);
=======

    @Override
    public List<Item> readItem(String name) {
        return itemRepository.findItemsByName(name);
>>>>>>> be3f54e64a47c057437609138322759d63eec9aa
    }

    @Override
    public void updateItem(Item item, String id) {

    }

    @Override
<<<<<<< HEAD
    public void deleteItem(long id) {
=======
    public void deleteItem(String id) {
>>>>>>> be3f54e64a47c057437609138322759d63eec9aa

    }
}
