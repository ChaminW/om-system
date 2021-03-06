package com.sysco.app.service;

import com.mongodb.MongoException;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.exception.DatabaseException;
import com.sysco.app.exception.EntityNotFoundException;
import com.sysco.app.exception.ErrorCode;
import com.sysco.app.exception.ValidationFailureException;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class OrderServiceImplTest {

    private Order order1, order2, order3;

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
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        order1 = new Order("res0001", "addr0001", "shipping", "pending", System.currentTimeMillis(), System.currentTimeMillis() + 1000000000, System.currentTimeMillis(), "", new ArrayList<String>() {{
            add("item0001");
        }});
        order1.setId("order0001");
        order2 = new Order("res0002", "addr0002", "pipeline", "approved", System.currentTimeMillis(), System.currentTimeMillis() + 1000000000, System.currentTimeMillis(), "", new ArrayList<String>() {{
            add("item0002");
        }});
        order2.setId("order0002");
        order3 = new Order("5a8112825c714934d46e8b7d", "123kj312c062cb49fbcd43ad8", "pipeline", "pending", System.currentTimeMillis(), System.currentTimeMillis() + 1000000000, System.currentTimeMillis(), "", new ArrayList<String>() {{
            add("5a5f72d2062cb49fbcd43ad9");
        }});
        pageRequest = PageRequest.of(0, 2);
    }


    @Test
    public void createOrderSubmitNewOrderThenSuccess() throws Exception {
        //order3 doesn't have an Id. Id will be auto generated in mongo db
        Order order = orderServiceWired.createOrder(order3);

        //check returned object Id value
        Assert.assertNotEquals(order.getId(), "");
        Assert.assertEquals(24,order.getId().length());
        Assert.assertEquals(order.getDeliveryAddressId(), order3.getDeliveryAddressId());
        Assert.assertEquals(order.getDeliveryMethod(), order3.getDeliveryMethod());
        Assert.assertEquals(order.getRestaurantId(), order3.getRestaurantId());
        Assert.assertEquals(order.getStatus(), order3.getStatus());
        Assert.assertEquals(order.getItemIdList().get(0), order3.getItemIdList().get(0));

    }

    @Test
    public void createOrderSubmitNewOrderWithIncorrectRestaurantIdThenValidationException() throws Exception {
        //below restaurant Id does not exists in database
        order3.setRestaurantId("fdafgdh3154");

        try {
            orderServiceWired.createOrder(order3);
            Assert.fail("testcase does not meet exception");
        } catch (ValidationFailureException ex) {
            Assert.assertEquals(ErrorCode.NO_RESTAURANT_EXIST_FAILURE,ex.getErrorCode());
        }

    }

    @Test
    public void readOrderByIdIncorrectIdNotFoundEx() {

        Mockito.when(orderRepository.findOrderById("order0001")).thenReturn(null);

        try {
            orderService.readOrder("order0001");
            Assert.fail("testcase does not meet exception");
        } catch (EntityNotFoundException ex) {
            Assert.assertEquals(ErrorCode.NO_ORDER_FOR_THE_ID,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrderByIdCorrectIdSuccessful() {

        Mockito.when(orderRepository.findOrderById("order0001")).thenReturn(order1);

        Order order = orderService.readOrder("order0001");
        Assert.assertEquals("res0001",order.getRestaurantId());
        Assert.assertEquals("addr0001",order.getDeliveryAddressId());
        Assert.assertEquals("item0001",order.getItemIdList().get(0));
    }

    @Test
    @Timed(millis = 1000)
    public void readAllOrdersTest() {

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> orders = orderService.readOrders();
        Assert.assertEquals(orders.size(), orderList.size());
        Assert.assertEquals(orders.get(0), order1);
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrderIncorrectIdNotFoundEx() {

        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(null);

        try {
            orderService.updateOrder("order0002", order1);
            Assert.fail("testcase does not meet exception");
        } catch (EntityNotFoundException ex) {
            Assert.assertEquals(ErrorCode.NO_ORDER_FOR_THE_ID,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrderTest() {

        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(order2);
        orderService.updateOrder("order0002", order1);
        Assert.assertEquals("addr0001",order2.getDeliveryAddressId());
        Assert.assertEquals("shipping",order2.getDeliveryMethod());
        Assert.assertEquals("pending",order2.getStatus());

    }

    //testing error codes when database exception raised
    @Test
    @Timed(millis = 1000)
    public void createOrderMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).insert(order1);
        try {
            orderService.createOrder(order1);
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_CREATE_FAILURE,ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrdersMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findAll();
        try {
            orderService.readOrders();
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_READ_FAILURE,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrdersPagebleMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findAll(pageRequest);
        try {
            orderService.readOrdersPageable(0, 2);
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_READ_FAILURE,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void updateOrderMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).save(order2);
        Mockito.when(orderRepository.findOrderById("order0002")).thenReturn(order2);
        try {
            orderService.updateOrder("order0002", order2);
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_UPDATE_FAILURE,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void deleteOrdersMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).deleteById("order0001");
        try {
            orderService.deleteOrderById("order0001");
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_DELETE_FAILURE,ex.getErrorCode());
        }
    }

    @Test
    @Timed(millis = 1000)
    public void readOrderByIdMongoErrorDatabaseException() {
        Mockito.doThrow(new MongoException("")).when(orderRepository).findOrderById("order0001");
        try {
            orderService.readOrder("order0001");
            Assert.fail("testcase does not meet exception");
        } catch (DatabaseException ex) {
            Assert.assertEquals(ErrorCode.ORDER_READ_FAILURE,ex.getErrorCode());
        }
    }

}