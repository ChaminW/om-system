package com.sysco.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.app.configuration.ApplicationConfiguration;
import com.sysco.app.model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
@WebAppConfiguration
public class OrderControllerTest {

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
    @Timed(millis = 1000)
    public void givenOrdersWhenMockMVCThenVerifyResponseOK() throws Exception {
        this.mockMvc.perform(get("/orders")
                .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();
    }

    /**
     * Integration testing for /orders/{id} API endpoint
     */
    @Test
    @Timed(millis = 1000)
    public void givenOrderByIdWithPathVariableWhenMockMVCThenResponseFOUND() throws Exception {
        this.mockMvc
                .perform(get("/orders/{id}", "5a8121965c7149562f3e96ee")
                        .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax"))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("5a8121965c7149562f3e96ee"))
                .andExpect(jsonPath("$.restaurantId").value("5a8112825c714934d46e8b7d"))
                .andExpect(jsonPath("$.deliveryAddressId").value("5a5f712c062cb49fbcd43ad8"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenOrderByIdWithPathVariableOfIncorrectFormatWhenMockMVCThenResponseBadRequest() throws Exception {
        this.mockMvc
                .perform(get("/orders/{id}", "ABCD*")
                        .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax"))
                .andDo(print()).andExpect(status().isBadRequest())

                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("Invalid pattern for order id"));
    }

    @Test
    public void givenOrdersWithPostAndFormDataWhenMockMVCThenResponseCREATED() throws Exception {
        Long currentTime = System.currentTimeMillis();
        Order order = new Order("5a8112825c714934d46e8b7d", "5a5f712c062cb49fbcd43ad8", "shipping", "pending", currentTime, currentTime + 5000000000000L , currentTime, "", new ArrayList<String>() {{
            add("5a5f411f062cb49fbcd43ad6");
        }});
        this.mockMvc.perform(post("/orders")
                .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.restaurantId").value("5a8112825c714934d46e8b7d"))
                .andExpect(jsonPath("$.deliveryAddressId").value("5a5f712c062cb49fbcd43ad8"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("pending"))
                .andExpect(jsonPath("$.validUntil").value(currentTime + 5000000000000L));
    }

    @Test
    @Timed(millis = 1000)
    public void givenOrdersWithPostAndFormDataWithInCorrectValidUntilDateWhenMockMVCThenResponseBadRequest() throws Exception {
        Long currentTime = System.currentTimeMillis();
        Order order = new Order("5a7b52c03235b718d1edcedo", "5a5f712c062cb49fbcd43ad8", "shipping", "pending",currentTime, currentTime - 5000000000000L , currentTime, "", new ArrayList<String>() {{
            add("5a5f72d2062cb49fbcd43ad9");
        }});
        this.mockMvc.perform(post("/orders")
                .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Timed(millis = 1000)
    public void givenUpdateOrdersWithPathVariableAndFormDataWhenMockMVCThenResponseUPDATED() throws Exception {
        Order order = new Order("5a7b52c03235b718d1edcedo", "5a5f3fec062cb49fbcd43ad5", "shipping", "approved", System.currentTimeMillis(), System.currentTimeMillis() + 1000000000, System.currentTimeMillis(), "", new ArrayList<String>() {{
            add("5a5f411f062cb49fbcd43ad6");
        }});
        this.mockMvc.perform(put("/orders/{id}", "5a807ad0136343325bde9702")
                .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.restaurantId").value("5a7b52c03235b718d1edcedo"))
                .andExpect(jsonPath("$.deliveryAddressId").value("5a5f3fec062cb49fbcd43ad5"))
                .andExpect(jsonPath("$.deliveryMethod").value("shipping"))
                .andExpect(jsonPath("$.status").value("approved"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenUpdateOrderWithPathVariableOfIncorrectFormatWhenMockMVCThenResponseBadRequest() throws Exception {
        Order order = new Order("213k705d062cb49fbc1j2kd7", "5a5f712c062cb49fbcd43ad8", "shipping", "pending", System.currentTimeMillis(), System.currentTimeMillis() + 1000000000, System.currentTimeMillis(), "", new ArrayList<String>() {{
            add("5a5f72d2062cb49fbcd43ad9");
        }});
        this.mockMvc
                .perform(put("/orders/{id}", "ABCD*")
                        .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))

                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("Invalid pattern for order id"));
    }

    @Test
    @Timed(millis = 1000)
    public void givenDeleteOrderWithPathVariableOfIncorrectFormatWhenMockMVCThenResponseBadRequest() throws Exception {
        this.mockMvc
                .perform(delete("/orders/{id}", "5a7ad86ea2684c44debeafdb")
                        .header("APIKEY", "yZwjdlsmaB0D7jdZpC8KO2YnOhLjhMax")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @After
    public void tearDown() {


    }
}