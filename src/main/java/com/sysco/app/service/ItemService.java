package com.sysco.app.service;

import com.sysco.app.model.Item;
import java.util.List;

public interface ItemService {

    void createItem(Item item);

    List<Item> readItems();

<<<<<<< HEAD
    List<Item> readItemsById(String id);
=======
    List<Item> readItem(String name);
>>>>>>> be3f54e64a47c057437609138322759d63eec9aa

    void updateItem(Item item,String id);

    void deleteItem(String id);

<<<<<<< HEAD
    void deleteItem(long id);
=======
>>>>>>> be3f54e64a47c057437609138322759d63eec9aa
}
