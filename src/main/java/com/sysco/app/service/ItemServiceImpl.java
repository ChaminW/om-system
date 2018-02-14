package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.exception.*;
import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import com.sysco.app.validator.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component("itemService")
public class ItemServiceImpl implements ItemService {

    private final
    ItemRepository itemRepository;

    private static final
    Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item) {
        item.setId(null);
        Item createdItem;
        try {
            createdItem = itemRepository.insert(item);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_CREATE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_CREATE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Item created ", createdItem.getId());
        return createdItem;
    }

    @Override
    public List<Item> readItems() {

        List<Item> items;
        try {
            items = itemRepository.findAll();
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Items retrieved");
        return items;
    }

    @Override
    public Page<Item> readItemsPageable(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> items;
        try {
            items = itemRepository.findAll(pageRequest);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Items retrieved");
        return items;
    }

    @Override
    public Item readItemById(String id) {

        if (!ItemValidator.isValidId(id)) {
            LOGGER.error(ErrorCode.ITEM_ID_VALIDATION_FAILURE.getDesc());
            throw new ValidationFailureException(this.getClass().getName(), ErrorCode.ITEM_ID_VALIDATION_FAILURE);
        }

        Item item;
        try {
            item = itemRepository.findItemById(id);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_READ_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_READ_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if (item == null) {
            LOGGER.info(ErrorCode.NO_ITEM_FOR_THE_ID.getDesc());
            throw new EntityNotFoundException(this.getClass().getName(), ErrorCode.NO_ITEM_FOR_THE_ID);
        }

        LOGGER.info("Item retrieved ", item.getId());
        return item;
    }

    @Transactional
    @Override
    public Item updateItem(String id, Item item) {

        if (!ItemValidator.isValidId(id)) {
            LOGGER.error(ErrorCode.ITEM_ID_VALIDATION_FAILURE.getDesc());
            throw new ValidationFailureException(this.getClass().getName(), ErrorCode.ITEM_ID_VALIDATION_FAILURE);
        }

        Item dbItem = readItemById(id);
        if (item.getName() != null) {
            dbItem.setName(item.getName());
        }
        if (item.getType() != null) {
            dbItem.setType(item.getType());
        }
        if (item.getPricePerItem() != null) {
            dbItem.setPricePerItem(item.getPricePerItem());
        }
        if (item.getTotalQuantity() != null) {
            dbItem.setTotalQuantity(item.getTotalQuantity());
        }
        if (item.getDescription() != null) {
            dbItem.setDescription(item.getDescription());
        }

        try {
            itemRepository.save(dbItem);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_UPDATE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_UPDATE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Item updated ", dbItem.getId());
        return dbItem;
    }

    @Transactional
    @Override
    public void deleteItemById(String id) {

        if (!ItemValidator.isValidId(id)) {
            LOGGER.error(ErrorCode.ITEM_ID_VALIDATION_FAILURE.getDesc());
            throw new ValidationFailureException(this.getClass().getName(), ErrorCode.ITEM_ID_VALIDATION_FAILURE);
        }

        try {
            itemRepository.deleteById(id);
        } catch (MongoException e) {
            LOGGER.error(ErrorCode.ITEM_DELETE_FAILURE.getDesc(), e);
            throw new DatabaseException(this.getClass().getName(), ErrorCode.ITEM_DELETE_FAILURE);
        } catch (Exception e) {
            LOGGER.error(ErrorCode.INTERNAL_SERVER_ERROR.getDesc(), e);
            throw new SystemException(this.getClass().getName(), ErrorCode.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("Item deleted ", id);
    }
}
