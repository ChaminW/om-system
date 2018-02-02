package com.sysco.app;

import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.configuration.MongoConfiguration;
import com.sysco.app.configuration.SwaggerConfiguration;
import com.sysco.app.model.Order;
import com.sysco.app.service.OrderService;
import com.sysco.app.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@ContextConfiguration( locations = "classpath:Beans.xml", classes = ApplicationConfiguration.class )
public class ApiTest {

    private String server;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;
    private Order order;

    @Qualifier("orderServiceImpl")
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @BeforeClass
    public void setUp()
    {
        order = new Order("5a5f705d062cb49fbcd43ad7","5a5f712c062cb49fbcd43ad8","aaa","asas", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f411f062cb49fbcd43ad6");}});
        rest = new RestTemplate();
        server = "http://localhost:10000/order";
        headers = new HttpHeaders();
        headers.add("Content-type","application/json");
    }

    //--response testing
    @Test
    public void testApiGet()
    {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", null);
        ResponseEntity<String> responseEntity = rest.exchange(server, HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
    }

    @Test
    public void testApiPost()
    {
        HttpEntity<Order> requestEntity = new HttpEntity<Order>(order,headers);
        ResponseEntity<Order> responseEntity = rest.exchange(server, HttpMethod.POST, requestEntity, Order.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        //Assert.assertEquals(order.getClass(),responseEntity.getBody().getClass());
    }

    @Test
    public void testWiring()
    {
        orderServiceImpl.readOrder();
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
