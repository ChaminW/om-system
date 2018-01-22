package com.sysco.app.repository;

import com.sysco.app.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("itemRepository")
public interface ItemRepository extends MongoRepository<Item, String> {
}
