package com.sysco.app.repository;

import com.sysco.app.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "itemRepository")
public interface ItemRepository extends MongoRepository<Item, String>, PagingAndSortingRepository<Item, String> {
    Item findItemById(String id);
}
