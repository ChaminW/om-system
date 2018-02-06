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

@Component("itemService")
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
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in creating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_CREATE_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public List<Item> readItems() {
        try {
            return itemRepository.findAll();
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public Page<Item> readItemsPageable(PageRequest pageRequest) {
        try {
            return itemRepository.findAll(pageRequest);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public Item readItemById(String id) {
        try {
            return itemRepository.findItemById(id);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            itemRepository.save(item);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.updateItem: Error in updating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_UPDATE_FAILURE, ItemServiceImpl.class);
        }
    }

    @Override
    public void deleteItem(String id) {
        try {

        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.deleteItem: Error in deleting";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_DELETE_FAILURE, ItemServiceImpl.class);
        }
    }


    public void addItem(){

    }
}
