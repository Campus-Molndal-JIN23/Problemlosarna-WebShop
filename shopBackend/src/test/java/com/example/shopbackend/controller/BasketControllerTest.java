package com.example.shopbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
@AutoConfigureMockMvc
class BasketControllerTest {

    private final String API = "/webshop/basket";
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @Test
    void getBasket() throws Exception {

        this.mvc.perform(get(API))
//                .contentType(PageAttributes.MediaType)
//                .content(mapper.writeValueAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void addProductToBasket() {



    }

    @Test
    void updateQuantity() {
    }

    @Test
    void deleteProduct() {
    }
}