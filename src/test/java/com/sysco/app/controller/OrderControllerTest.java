package com.sysco.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.model.Order;
import com.sysco.app.repository.ItemRepository;
import com.sysco.app.repository.OrderRepository;
import com.sysco.app.service.ItemServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"test"})
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@WebAppConfiguration
public class OrderControllerTest{

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * Integration testing for /orders API endpoint
     */
    @Test
    @Timed(millis=1000)
    public void givenOrders_whenMockMVC_thenVerifyResponseOK() throws Exception {
        this.mockMvc.perform(get("/orders"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();

        //      Can be used for test functions which Spring test library does not provide
        //      Assert.assertEquals("application/json;charset=UTF-8",
        //      mvcResult.getResponse().getContentType());
    }

    /**
     * Integration testing for /orders/{id} API endpoint
     */
    @Test
    @Timed(millis=1000)
    public void givenOrderByIdWithPathVariable_whenMockMVC_thenResponseFOUND() throws Exception {
        this.mockMvc
                .perform(get("/orders/{id}", "5a65da6ba9e34b389546fd12"))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("5a65da6ba9e34b389546fd12"))
                .andExpect(jsonPath("$.restaurantId").value("5a5f705d062cb49fbcd43ad7"))
                .andExpect(jsonPath("$.deliveryAddressId").value("5a5f712c062cb49fbcd43ad8"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenOrderByIdWithPathVariableOfIncorrectFormat_whenMockMVC_thenResponseBadRequest() throws Exception
    {
        this.mockMvc
                .perform(get("/orders/{id}","ABCD*"))
                .andDo(print()).andExpect(status().isBadRequest())

                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("getOrderById.id: Id should be of varchar type"));
    }



    @Test
    public void givenOrdersWithPostAndFormData_whenMockMVC_thenResponseCREATED() throws Exception {
        Order order = new Order("5a7febf9638e3868962c17af","123kj312c062cb49fbcd43ad8","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f411f062cb49fbcd43ad6");}});
        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.restaurantId").value("5a7febf9638e3868962c17af"))
                .andExpect(jsonPath("$.deliveryAddressId").value("123kj312c062cb49fbcd43ad8"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenOrdersWithPostAndFormDataInInvalidFormat_whenMockMVC_thenResponseBadRequest() throws Exception {
        Order order = new Order(null,"123kj312c062cb49fbcd43ad8","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f72d2062cb49fbcd43ad9");}});
        //pass null value as the restaurant Id
        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Timed(millis = 1000)
    public void givenOrdersWithPostAndFormDataWithNoRestaurant_whenMockMVC_thenResponseBadRequest() throws Exception{
        Order order = new Order("5a7b52c03235b718d1edcedo","123kj312c062cb49fbcd43ad8","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f72d2062cb49fbcd43ad9");}});
        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUpdateOrdersWithPathVariableAndFormData_whenMockMVC_thenResponseUPDATED() throws Exception {
        Order order = new Order("213k705d062cb49fbc1j2kd7","123kj312c062cb49fbcd43ad8","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f411f062cb49fbcd43ad6");}});
        this.mockMvc.perform(put("/orders/{id}","213k705d062cb49fbc1j2kd7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.restaurantId").value("213k705d062cb49fbc1j2kd7"))
                .andExpect(jsonPath("$.deliveryAddressId").value("123kj312c062cb49fbcd43ad8"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenUpdateOrderWithPathVariableOfIncorrectFormat_whenMockMVC_thenResponseBadRequest() throws Exception
    {
        Order order = new Order("213k705d062cb49fbc1j2kd7","123kj312c062cb49fbcd43ad8","shipping","pending", Date.from(Instant.now()),Date.from(Instant.now()),Date.from(Instant.now()),"",new ArrayList<String>(){{add("5a5f72d2062cb49fbcd43ad9");}});
        this.mockMvc
                .perform(put("/orders/{id}","ABCD*")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))

                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("updateOrder.id: Id should be of varchar type"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenDeleteOrderWithPathVariableOfIncorrectFormat_whenMockMVC_thenResponseBadRequest() throws Exception
    {
        this.mockMvc
                .perform(delete("/orders/{id}","5a7ad86ea2684c44debeafdb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
                }

    @After
    public void tearDown() {


    }
}