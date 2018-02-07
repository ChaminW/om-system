package com.sysco.app.service;

import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import javafx.beans.binding.When;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Timed;
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
    private Order order1,order2;

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Before
    public void setUp()
    {
        pageRequest = new PageRequest(0,2);
        MockitoAnnotations.initMocks(this);
        order1 = new Order("res0001","addr0001","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("item0001");}});
        order1.setId("order0001");
        order2 = new Order("res0002","addr0002","pipeline","approved", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("item0002");}});
        order2.setId("order0002");
    }


   /* @Test
    @Timed(millis = 1000)
    public void createOrder_passNull_thenCreateFailure(){
        try {
            orderService.createOrder(null);
        }
        catch (DatabaseException ex) {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.createOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(), ErrorCode.ORDER_CREATE_FAILURE);
        }
    }*/

    /*@Test
    @Timed(millis = 1000)
    public void createOrder_passOrderOb_thenSuccess(){

        Mockito.when(orderRepository.insert(order1)).thenReturn(order1);
        //Order order = orderService.createOrder(order1);
    }*/

    /*@Test
    @Timed(millis = 1000)
    public void deleteOrderById_passIncorrectId_notFoundEx(){

    }*/

    @Test
    @Timed(millis = 1000)
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
    @Timed(millis = 1000)
    public void readOrderById_IncorrectId_NotFoundEx(){

        Mockito.when(orderRepository.findOrderById("order0001")).thenReturn(order1);

        try{
            orderService.readOrder("order0002");
        }
        catch (EntityNotFoundException ex)
        {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.readOrder: Empty order");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.NO_ORDER_FOR_THE_ID);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrderById_correctId_successful(){

        Mockito.when(orderRepository.findOrderById("order0001")).thenReturn(order1);

        Order order = orderService.readOrder("order0001");
        Assert.assertEquals(order.getRestaurantId(),"res0001");
        Assert.assertEquals(order.getDeliveryAddressId(),"addr0001");
        Assert.assertEquals(order.getItemIdList().get(0),"item0001");
    }

    @Test
    @Timed(millis = 1000)
    public void readAllOrdersTest(){

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> orders = orderService.readOrders();
        Assert.assertEquals(orders.size(),orderList.size());
        Assert.assertEquals(orders.get(0),order1);
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrder_IncorrectId_NotFoundEx(){

        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(null);

        try{
            orderService.updateOrder("order0002",order1);
        }
        catch (EntityNotFoundException ex)
        {
            Assert.assertEquals(ex.getDebugMessage(),"OrderServiceImpl.readOrder: Empty order");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.NO_ORDER_FOR_THE_ID);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrderTest(){

        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(order2);
        orderService.updateOrder("order0002",order1);

        Assert.assertEquals(order2.getRestaurantId(),"res0001");
        Assert.assertEquals(order2.getDeliveryAddressId(),"addr0001");
        Assert.assertEquals(order2.getDeliveryMethod(),"shipping");
        Assert.assertEquals(order2.getStatus(),"pending");

    }

}