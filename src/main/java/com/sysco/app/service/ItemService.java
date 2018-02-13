package com.sysco.app.service;

import com.sysco.app.model.Item;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ItemService {

    Item createItem(Item item);

    List<Item> readItems();

    Page<Item> readItemsPageable(int page, int size);

    Item readItemById(String id);

    Item updateItem(String id, Item item);

    void deleteItemById(String id);
}
