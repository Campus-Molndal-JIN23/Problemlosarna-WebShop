package com.example.shopbackend.controller;

import com.example.shopbackend.form.LoginForm;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final String API = "/webshop/order";
    private final String API2 = "/webshop/orders";

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mvc;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        var loginPayload = new LoginForm("name3", "Password1"); // replace with valid credentials
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
    void order() throws Exception {

        this.mvc.perform(post(API)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOrder() throws Exception {
        this.mvc.perform(get(API)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOrders() throws Exception {

        this.mvc.perform(get(API2)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}