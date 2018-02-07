package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exceptions.DatabaseException;
import com.sysco.app.exceptions.ErrorCode;
import com.sysco.app.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = ApplicationConfiguration.class )
@WebAppConfiguration
public class OrderServiceImplTest {

    @Qualifier("orderService")
    @Autowired
    private OrderService orderService;
    private PageRequest pageRequest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp()
    {
        pageRequest = new PageRequest(0,2);
    }

    @Test
    public void givencreateOrder_whenPassNull_thenThrowException(){

        /*try {
            orderService.createOrder(null);
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertEquals(ex.getMessage(),"Entity must not be null!");
        }*/
    }

    @Test
    public void givencreateOrder_whenPassNewObject_thenCreateFailure1(){
        try {
            orderService.createOrder(new Order());
        }
        catch (DatabaseException ex) {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.createOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(), ErrorCode.ORDER_CREATE_FAILURE);
        }
    }


}
