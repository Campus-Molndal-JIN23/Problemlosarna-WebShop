package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Product;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private final String API = "/webshop/products";

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mvc;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        var loginPayload = new LoginForm("admin", "Password1"); // replace with valid credentials
        MvcResult result = this.mvc.perform(post("/webshop/auth/login") // replace with your login endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginPayload)))
                .andReturn();

        int status = result.getResponse().getStatus();
        if (status != 200) {
            throw new RuntimeException("Login request failed with status code: " + status);
        }

        String response = result.getResponse().getContentAsString();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Login response is null or empty");
        }
        // assuming the response is a JSON object with a field "token" containing the JWT token
        this.token = JsonPath.parse(response).read("$.token");
        if (this.token == null || this.token.isEmpty()) {
            throw new RuntimeException("JWT token is null or empty");
        }
    }

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
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void FailToCreateOne() throws Exception {
        this.mvc.perform(post(API)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
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
        payload.setDescription("Updated description");


        this.mvc.perform(put(API)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneSuccess() throws Exception {
        var payload = new ProductDTO(7L, "name", "desc", 54);

        this.mvc.perform(delete(API)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status()
                        .isNoContent());
    }

    @Test
    void deleteOneNotFound() throws Exception {
        var payload = new ProductDTO(99855L, "name", "desc", 54);

        this.mvc.perform(delete(API)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status()
                        .isNotFound());
    }
}