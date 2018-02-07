package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.controller.ItemController;
import com.sysco.app.exceptions.DatabaseException;
import com.sysco.app.exceptions.EntityNotFoundException;
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

        // Create item
        try {
            itemRepository.insert(item);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in creating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_CREATE_FAILURE, ItemServiceImpl.class);
        }

        LOGGER.info("Item added", item);
    }

    @Override
    public List<Item> readItems() {

        List<Item> items;

        // Read items
        try {
            items = itemRepository.findAll();
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }

        return items;
    }

    @Override
    public Page<Item> readItemsPageable(int page, int size) {

        // Initiate a page request
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> items;

        // Read items
        try {
            items = itemRepository.findAll(pageRequest);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }

        LOGGER.info("Items retrieved");

        return items;
    }

    @Override
    public Item readItemById(String id) {

        Item item;

        // Read item for the given id
        try {
            item = itemRepository.findItemById(id);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.readItemsPageable: Error in reading";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_READ_FAILURE, ItemServiceImpl.class);
        }

        // If there is no item for the given id
        if(item == null){
            String errorMessage = "ItemServiceImpl.readItemsPageable: Empty item";
            LOGGER.info(errorMessage);
            throw new EntityNotFoundException(errorMessage,
                    ErrorCode.NO_ITEM_FOR_THE_ID, ItemController.class);
        }

        LOGGER.info("Item retrieved", item);

        return item;
    }

    @Override
    public void updateItem(String id, Item item) {

        // Read item for the given id
        Item newItem = readItemById(id);

        // Setup parameters
        if(item.getName() != null) {
            newItem.setName(item.getName());
        }
        if(item.getType() != null) {
            newItem.setType(item.getType());
        }
        if(item.getPricePerItem() != null) {
            newItem.setPricePerItem(item.getPricePerItem());
        }
        if(item.getTotalQuantity() != null) {
            newItem.setTotalQuantity(item.getTotalQuantity());
        }
        if(item.getDescription() != null) {
            newItem.setDescription(item.getDescription());
        }

        // Update item
        try {
            itemRepository.save(newItem);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.updateItem: Error in updating";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_UPDATE_FAILURE, ItemServiceImpl.class);
        }

        LOGGER.info("Item updated");
    }

    @Override
    public void deleteItemById(String id) {

        // Delete item for a given id
        try {
            itemRepository.deleteById(id);
        } catch (MongoException e) {
            String errorMessage = "ItemServiceImpl.deleteItem: Error in deleting";
            LOGGER.error(errorMessage, e);
            throw new DatabaseException(errorMessage,
                    ErrorCode.ITEM_DELETE_FAILURE, ItemServiceImpl.class);
        }
    }
}
