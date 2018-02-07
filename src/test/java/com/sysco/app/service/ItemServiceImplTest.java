package com.sysco.app.service;

import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.model.Item;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = ApplicationConfiguration.class )
@WebAppConfiguration
public class ItemServiceImplTest {

    @Qualifier("itemService")
    @Autowired
    private ItemService itemService;

    private Item item;

    @Before
    public void setUp()
    {
        itemService.deleteItemById("5a5f411f062cb49fbcd43ad6");
        item = new Item("Test Name", "Test Type", 100.0,
                100.0, "Test Description");
        item.setId("5a5f411f062cb49fbcd43ad6");
        itemService.createItem(item);
    }

    // method-name_testing-feature_expectation().
    @Test
    public void givenReadItemById_whenMongoServiceStopped_thenMongoException() {
        Item newItem = itemService.readItemById("5a5f411f062cb49fbcd43ad6");
        Assert.assertEquals(item.getId(), newItem.getId());
    }

    @After
    public void clean() {
        itemService.deleteItems(item);
    }
}
