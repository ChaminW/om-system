package com.sysco.app.service;

import com.sysco.app.model.Item;
import java.util.List;

public interface ItemService {

    void createItem(Item item);

    List<Item> readItems();

    List<Item> readItemsById(String id);

    void updateItem(Item item);

    void deleteItem(Item id);

    void deleteItem(long id);
}
