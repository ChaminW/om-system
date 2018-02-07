package com.sysco.app.service;

import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = ApplicationConfiguration.class )
@WebAppConfiguration
public class OrderServiceImplTest {

    private PageRequest pageRequest;
    private Order order;

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp()
    {
        pageRequest = new PageRequest(0,2);
        MockitoAnnotations.initMocks(this);
        order = new Order("5a5f705d062cb49fbcd43ad7","5a5f712c062cb49fbcd43ad8","aaa","asas", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f411f062cb49fbcd43ad6");}});
        order.setId("123");
    }

    @Test
    public void createOrder_passNewObject_thenCreateFailure(){
        try {
            orderService.createOrder(new Order());
        }
        catch (DatabaseException ex) {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.createOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(), ErrorCode.ORDER_CREATE_FAILURE);
        }
    }

    @Test
    public void readOrderById_IncorrectId_OrderNotFoundEx(){

        try{
            orderService.readOrder("aaaaaaa");
        }
        catch (EntityNotFoundException ex)
        {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.readOrder: Empty order");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.NO_ORDER_FOR_THE_ID);
        }
    }

    @Test
    public void readOrderById(){
        Mockito.when(orderRepository.findOrderById("123")).thenReturn(order);

        Order order = orderService.readOrder("123");
        Assert.assertEquals(order.getId(),"123");
       // assertThat("Size", userList.size(), equalTo(resultUserList.size());
       // assertEquals("Details",details, equalTo(resultUserList.get(0)));
        //orderService.readOrders();

    }



}
