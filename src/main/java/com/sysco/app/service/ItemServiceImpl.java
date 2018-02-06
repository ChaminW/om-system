package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exceptions.DatabaseException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    @Qualifier("itemRepository")
    @Autowired
    ItemRepository itemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void createItem(Item item) {
        try {
            itemRepository.insert(item);
        } catch (MongoException e) {
            LOGGER.error("Cannot create item", e);
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Error in creating",
                    ErrorCode.ITEM_CREATE_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public List<Item> readItems() {
        try {
            return itemRepository.findAll();
        } catch (MongoException e) {
            LOGGER.error("Cannot read items", e);
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Error in reading",
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public Page<Item> readItemsPageable(PageRequest pageRequest) {
        try {
            return itemRepository.findAll(pageRequest);
        } catch (MongoException e) {
            LOGGER.error("Cannot read items", e);
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Error in reading",
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public Item readItemById(String id) {
        try {
            return itemRepository.findItemById(id);
        } catch (MongoException e) {
            LOGGER.error("Cannot read item", e);
            throw new DatabaseException("ItemServiceImpl.readItemsPageable: Error in reading",
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public void updateItem(Item item, String id) {

    }

    @Override
    public void deleteItem(String id) {

    }


    public void addItem(){

    }
}
