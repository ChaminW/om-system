package com.sysco.app.repository;

import com.sysco.app.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
<<<<<<< HEAD
    List<Item> findItemsById(String id);
=======
    List<Item> findItemsByName(String name);

>>>>>>> be3f54e64a47c057437609138322759d63eec9aa
}
