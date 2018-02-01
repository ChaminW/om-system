package com.sysco.app;

import com.sysco.app.model.Item;
import com.sysco.app.model.Order;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.MessageFormat;
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

    @Test
    public void testApiGet()
    {
        HttpEntity<String> httpEntity = new HttpEntity<String>("", null);
        ResponseEntity<String> responseEntity = rest.exchange(orderServiceEndpoint, HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    public void testApiPost()
    {
        HttpEntity<Order> httpEntity = new HttpEntity<Order>(order,headers);
        ResponseEntity<Order> responseEntity = rest.exchange(orderServiceEndpoint, HttpMethod.POST, httpEntity, Order.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void itemPost() {
        HttpEntity<Item> httpEntity = new HttpEntity<Item>(item, headers);
        ResponseEntity<Item> responseEntity = rest.exchange(itemServiceEndpoint, HttpMethod.POST,httpEntity, Item.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void itemGetAll() {
        HttpEntity<String> httpEntity = new HttpEntity<String>("", null);
        ResponseEntity<String> responseEntity = rest.exchange(itemServiceEndpoint, HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void itemGetById() {
        HttpEntity<String> httpEntity = new HttpEntity<>("", null);
        ResponseEntity<String> responseEntity = rest.exchange(itemServiceEndpoint + "/{item_id}", HttpMethod.GET, httpEntity, String.class, "5a6947a326a0f6c320005e74");
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
    }


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
