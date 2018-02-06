package com.sysco.app.controller;

import com.sysco.app.configuration.ApplicationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
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
    public void givenOrders_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/orders"))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();

        //Can be used for test functions which Spring test library does not provide
        //Assert.assertEquals("application/json;charset=UTF-8",
        //        mvcResult.getResponse().getContentType());
    }

    /**
     * Integration testing for /orders/{id} API endpoint
     */
    @Test
    public void givenOrderByIdWithPathVariable_whenMockMVC_thenResponseFOUND() throws Exception {
        this.mockMvc
                .perform(get("/orders/{id}", "5a65da6ba9e34b389546fd12"))
                .andDo(print()).andExpect(status().isFound())

                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("5a65da6ba9e34b389546fd12"))
                .andExpect(jsonPath("$.restaurantId").value("5a5f705d062cb49fbcd43ad7"));
    }

    @After
    public void tearDown() {
    }
}