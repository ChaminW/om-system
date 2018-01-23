package com.sysco.app.service;

import com.sysco.app.model.Item;
import java.util.List;

public interface ItemService {

    void createItem(Item item);

    List<Item> readItem();

    List<Item> readItem(Item item);

    List<Item> readItem(long id);

    List<Item> readItem(String name);

    void updateItem(Item item);

    void deleteItem(Item id);

    void delteItem(long id);
}
