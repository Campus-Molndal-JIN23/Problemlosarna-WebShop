package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        this.mvc.perform(get(API)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addProductToBasket() throws Exception {
        var newProd = new ProductDTO(new Product("new product", 12, "flavorText"), 39);

        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newProd))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuantity() throws Exception {

        var updateProdQty = new ProductDTO(new Product("Product 1", 100, "Text about the product 1"), 999);
        System.out.println(mapper.writeValueAsString(updateProdQty));
        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateProdQty))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void deleteProduct() throws Exception {
        var payload = new Product("Product 1", 100, "Text about the product 1");
        this.mvc.perform(delete(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}