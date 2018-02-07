package com.sysco.app.service;

import com.sysco.app.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;

public interface ItemService {

    void createItem(Item item);

    List<Item> readItems();

    Page<Item> readItemsPageable(int page, int size);

    Item readItemById(String id);

    void updateItem(String id, Item item);

    void deleteItemById(String id);
}
