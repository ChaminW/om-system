package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.model.Order;
import com.sysco.app.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"test"})
@ContextConfiguration( classes = ApplicationConfiguration.class )
@WebAppConfiguration
public class OrderServiceImplTest {

    private Order order1,order2,order3;

    @Autowired
    @Qualifier("orderService")
    OrderService orderServiceWired;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PageRequest pageRequest;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        order1 = new Order("res0001","addr0001","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("item0001");}});
        order1.setId("order0001");
        order2 = new Order("res0002","addr0002","pipeline","approved", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("item0002");}});
        order2.setId("order0002");
        order3 = new Order("213k705d062cb49fbc1j2kd7","123kj312c062cb49fbcd43ad8","pipeline","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f72d2062cb49fbcd43ad9");}});
        pageRequest = PageRequest.of(0, 2);
    }


    @Test
    public void createOrder_submitNewOrder_thenSuccess(){
        //order3 doesn't have an Id. Id will be auto generated in mongo db
        Order order = orderServiceWired.createOrder(order3);

        //check returned object Id value
        Assert.assertNotEquals(order.getId(),"");
        Assert.assertEquals(order.getId().length(),24);
        Assert.assertEquals(order.getDeliveryAddressId(),order3.getDeliveryAddressId());
        Assert.assertEquals(order.getDeliveryMethod(),order3.getDeliveryMethod());
        Assert.assertEquals(order.getRestaurantId(),order3.getRestaurantId());
        Assert.assertEquals(order.getStatus(),order3.getStatus());
        Assert.assertEquals(order.getItemIdList().get(0),order3.getItemIdList().get(0));

    }

    @Test
    public void readOrderById_IncorrectId_NotFoundEx(){

        Mockito.when(orderRepository.findOrderById("order0001")).thenReturn(null);

        try{
            orderService.readOrder("order0001");
            Assert.fail("testcase does not meet exception");
        }
        catch (EntityNotFoundException ex)
        {
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
            Assert.fail("testcase does not meet exception");
        }
        catch (EntityNotFoundException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.readOrder: Empty order");
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

    //testing error codes when database exception raised
    @Test
    @Timed(millis = 1000)
    public void createOrder_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).insert(order1);
        try{
            orderService.createOrder(order1);
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.createOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_CREATE_FAILURE);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrders_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findAll();
        try{
            orderService.readOrders();
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.readOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_READ_FAILURE);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrdersPageble_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findAll(pageRequest);
        try{
            orderService.readOrdersPageable(0,2);
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.readOrdersPageable: Error in reading");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_READ_FAILURE);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrder_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).save(order2);
        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(order2);
        try{
            orderService.updateOrder("order0002",order2);
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.updateOrder: Error in updating");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_UPDATE_FAILURE);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void deleteOrders_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).deleteById("order0001");
        try{
            orderService.deleteOrderById("order0001");
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.deleteOrder: Error in deleting");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_DELETE_FAILURE);
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readrderById_mongoError_DatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findOrderById("order0001");
        try{
            orderService.readOrder("order0001");
            Assert.fail("testcase does not meet exception");
        }
        catch (DatabaseException ex)
        {
            Assert.assertEquals(ex.getMessage(),"OrderServiceImpl.readOrder: Error in reading");
            Assert.assertEquals(ex.getErrorCode(),ErrorCode.ORDER_READ_FAILURE);
        }
    }

}