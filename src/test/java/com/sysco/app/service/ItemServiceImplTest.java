package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"test"})
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class ItemServiceImplTest {

    @Mock
    private Item item;
    @Mock
    private PageRequest pageRequest;
    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemServiceImpl itemService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        item = new Item();
        item.setId("123");
        item.setName("Test Item");
        item.setType("Test Type");
        item.setPricePerItem(100.0);
        item.setTotalQuantity(20.0);
        item.setDescription("Test Description");

        pageRequest = PageRequest.of(0, 2);
    }

    @Test(expected = DatabaseException.class)
    public void createItem_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(itemRepository).insert(item);
        itemService.createItem(item);
    }

    @Test(expected = DatabaseException.class)
    public void readItems_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(itemRepository).findAll();
        itemService.readItems();
    }

    @Test(expected = DatabaseException.class)
    public void readItemsPageable_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(itemRepository).findAll(pageRequest);
        itemService.readItemsPageable(0, 2);
    }

    @Test(expected = DatabaseException.class)
    public void readItemById_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(itemRepository).findItemById("123");
        itemService.readItemById("123");
    }

    @Test(expected = DatabaseException.class)
    public void updateItem_mongoError_DatabaseException() {
        Mockito.when(itemRepository.findItemById("123")).thenReturn(item);
        Mockito.doThrow(new MongoException("")).when(itemRepository).save(item);
        itemService.updateItem("123", item);
    }

    @Test(expected = DatabaseException.class)
    public void deleteItemById_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(itemRepository).deleteById("123");
        itemService.deleteItemById("123");
    }

    @Test
    public void readItemById_validExistingId_Item() {
        Mockito.when(itemRepository.findItemById("123")).thenReturn(item);
        Item newItem = itemService.readItemById("123");
        Assert.assertEquals(item.getId(), newItem.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void readItemById_validNonExistingId_EntityNotFoundException() {
        Item newItem = itemService.readItemById("1234");
    }
}
