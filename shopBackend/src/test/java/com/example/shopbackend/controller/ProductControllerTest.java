package com.example.shopbackend.controller;

import com.example.shopbackend.entity.ProductOld;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest({ProductController.class, ProductService.class, ProductRepository.class})
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private final String API = "/webshop/products";

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @Test
    void getAllProducts() throws Exception {
        this.mvc.perform(get(API))
                .andExpect(status().isOk());


    }

    @Test
    void getOne() throws Exception {
        this.mvc.perform(get(API + "/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    void postCreateOne() throws Exception {
        var payload = new ProductOld("A created product", 42, "Not the product you sent but a generic return");
//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void putUpdateOne() throws Exception {
        var payload = new ProductOld("A updated product", 42, "Not the product you sent but a generic return");
//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(put(API + "/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteOne() throws Exception {
        this.mvc.perform(delete(API + "/" + 1))
                .andExpect(status()
                        .isNoContent());

    }
}