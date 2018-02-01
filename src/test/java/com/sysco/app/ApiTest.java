package com.sysco.app;

import com.sysco.app.model.Item;
import com.sysco.app.model.Order;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class ApiTest {

    private String orderServiceEndpoint;
    private String itemServiceEndpoint;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;
    private Order order;
    private Item item;

    @BeforeClass
    public void setUp()
    {
        order = new Order("5a5f705d062cb49fbcd43ad7","5a5f712c062cb49fbcd43ad8","aaa","asas", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f411f062cb49fbcd43ad6");}});// String name, String type, Double pricePerItem, Double totalQuantity, String description
        item = new Item("Test Item", "Test Type", 12.0, 20.0, "Test Description");
        rest = new RestTemplate();
        orderServiceEndpoint = "http://localhost:8082/orders";
        itemServiceEndpoint = "http://localhost:8082/items";
        headers = new HttpHeaders();
        headers.add("Content-type","application/json");
    }

    //--response testing
    @Test
    public void testApiGet()
    {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", null);
        ResponseEntity<String> responseEntity = rest.exchange(orderServiceEndpoint, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    public void testApiPost()
    {
        HttpEntity<Order> requestEntity = new HttpEntity<Order>(order,headers);
        ResponseEntity<Order> responseEntity = rest.exchange(orderServiceEndpoint, HttpMethod.POST, requestEntity, Order.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        //Assert.assertEquals(order.getClass(),responseEntity.getBody().getClass());
    }

    @Test
    public void addItem(){
        HttpEntity<Item> entity = new HttpEntity<Item>(item, headers);
        ResponseEntity<Item> responseEntity = rest.exchange(itemServiceEndpoint, HttpMethod.POST,entity, Item.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    //

  /*  public String post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void put(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity, null);
        this.setStatus(responseEntity.getStatusCode());
    }

    public void delete(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity, null);
        this.setStatus(responseEntity.getStatusCode());
    }*/
}
