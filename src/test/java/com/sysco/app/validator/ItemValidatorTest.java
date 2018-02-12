package com.sysco.app.validator;

import com.sysco.app.model.Item;
import com.sysco.app.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

public class ItemValidatorTest {

    @Mock
    private Item item;

    @Mock
    ItemRepository itemRepository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        item = new Item();
        item.setId("5a5f72d2062cb49fbcd43ad9");
        item.setName("Test Item");
        item.setType("Test Type");
        item.setPricePerItem(100.0);
        item.setTotalQuantity(20.0);
        item.setDescription("Test Description");
    }

    @Test
    public void isValidId() {
        Assert.assertTrue(ItemValidator.isValidId("5a5f72d2062cb49fbcd43ad9"));
    }

    @Test
    public void isIdExist() {
        Mockito.when(itemRepository.findItemById("5a5f72d2062cb49fbcd43ad9")).thenReturn(item);
        Assert.assertTrue(ItemValidator.isIdExist("5a5f72d2062cb49fbcd43ad9"));
    }

    @Test
    public void isIdsExist() {
    }
}