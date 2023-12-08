package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    void ProductDoesNotExistShouldFail() throws Exception {
        this.mvc.perform(get(API + "/" + 99999993434L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isNotFound());
    }


    @Test
    void postCreateOne() throws Exception {

        var payload = new Product("A created product", "Not the product you sent but a generic return", 42);

//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void FailToCreateOne() throws Exception {
//        var payload = new ProductOld("A created product", 42, "Not the product you sent but a generic return");
//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Disabled("Changed requirements no @PathVariable for endpoint")
    void FailToUpdateOneBecauseObjectIdIsNull() throws Exception {
        var payload = new Product("A updated product", "Not the product you sent but a generic return", 42);
//        System.out.println(mapper.writeValueAsString(payload));
        this.mvc.perform(put(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateOneSuccess() throws Exception {
        // grab a Product to update
        MvcResult result = this.mvc.perform(get(API + "/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Product payload = mapper.readValue(responseBody, Product.class);

        // update properties
        payload.setName("A updated product");
        payload.setPrice(69);
        payload.setShortDescription("Updated description");


        this.mvc.perform(put(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneSuccess() throws Exception {
        // grab a Product to update
    /*    MvcResult result = this.mvc.perform(get(API + "/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Product payload = mapper.readValue(responseBody, Product.class);
*/

//        Delete it
        this.mvc.perform(delete(API + "/" + 1L))
                .andExpect(status()
                        .isNoContent());

    }

    @Test
    void deleteOneNotFound() throws Exception {
        this.mvc.perform(delete(API + "/" + 9836476049L))
                .andExpect(status()
                        .isNotFound());

    }
}