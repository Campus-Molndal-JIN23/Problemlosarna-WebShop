package com.example.shopbackend.controller;

import com.example.shopbackend.form.UpdateBasketDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    private final String API = "/webshop/basket";
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @Test
    void getBasket() throws Exception {
        this.mvc.perform(get(API)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addProductToBasket() throws Exception {
        var payload = new UpdateBasketDTO(1L, 99);
        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuantity() throws Exception {

        var payload = new UpdateBasketDTO(3L, 999);
        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(put(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        var payload = new UpdateBasketDTO(2L, 0);
//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(delete(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}