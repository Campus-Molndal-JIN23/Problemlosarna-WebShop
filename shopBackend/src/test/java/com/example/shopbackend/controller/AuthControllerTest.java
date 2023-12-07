package com.example.shopbackend.controller;

import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.UserTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;
    @Test
    void register() throws Exception {
        String API = "/webshop/auth/register";
        LoginForm loginForm = new LoginForm("a","b");
        String json = mapper.writeValueAsString(loginForm);

        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                        .andExpect(status().isNoContent());

    }

    @Test
    void login() throws Exception {
        String API = "/webshop/auth/login";
        LoginForm loginForm = new LoginForm("a","b");
        String json = mapper.writeValueAsString(loginForm);

        MvcResult result = this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals(content,"JWTToken"+"Admin"+"Username");


    }
}