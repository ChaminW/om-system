package com.sysco.app.service;

import com.sysco.app.configuration.ApplicationConfiguration;
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

    @Before
    public void setUp()
    {

    }

    @Test(expected = Exception.class)
    public void givenReadItemById_whenMongoServiceStopped_thenMongoException() {
        itemService.readItems();
    }
}
