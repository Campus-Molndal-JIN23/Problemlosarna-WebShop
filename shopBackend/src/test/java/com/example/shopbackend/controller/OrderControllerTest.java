package com.example.shopbackend.controller;

import com.example.shopbackend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final String API = "/webshop/order";
    private final String API2 = "/webshop/orders";

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @Test
    void order() throws Exception {
       User user = new User();

        this.mvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void getOrder() throws Exception {
        this.mvc.perform(get(API)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOrders() throws Exception {

        this.mvc.perform(get(API2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}