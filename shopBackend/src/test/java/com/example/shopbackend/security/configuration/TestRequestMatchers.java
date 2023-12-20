package com.example.shopbackend.security.configuration;

import com.example.shopbackend.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestRequestMatchers {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();
    ProductDTO product;
}
