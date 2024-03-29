package com.example.shopbackend.security.configuration;

import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SecurityConfigurationTest {
    @Autowired
    MockMvc mvc;


    ObjectMapper mapper = new ObjectMapper();
    ProductDTO product;
    String token;
    private String clientUsername;
    private String clientPassword;
    private String adminUsername;
    private String adminPassword;

    @BeforeEach
    void setUp() throws Exception {

        clientUsername = "ana@gmail.com";
        clientPassword = "123456";
        adminUsername = "bob@gmail.com";
        adminPassword = "123456";
        String jwtSigningKey = "0b3a2839ae978330c29d866068171086524b7cd5a7e8a7ddc5fd7a893f7caf7b";
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", Collections.singleton("ADMIN"));
        claims.put("userID", 1L);
        claims.put("username", "USER");

        token = Jwts.builder()
                .setClaims(claims)
                .setSubject("USER")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void securityFilterChain() throws Exception {

        String API = "/webshop/auth/register";
        LoginForm loginForm = new LoginForm("user", "Password1");
        String json = mapper.writeValueAsString(loginForm);

        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }

//    @Test
//    void UserWithRoleAdminCanGETToProducts() throws Exception {
//        User user = new User();
//        user.setUserName(clientUsername);
//        user.setId(1);
//        user.setPassword(clientPassword);
//        user.setRole("ADMIN");
//
//        System.out.println(token);
//
//        var payload = new Product(1L, "Apple", "Frukt", 39);
//        String json = mapper.writeValueAsString(payload);
//        mvc.perform(post("/webshop/products/")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }


//    @Test
//    void UserWithRoleAdminCanPostToProducts() throws Exception {
//        var payload = new Product(1L, "Apple", "Frukt", 39);
//        String json = mapper.writeValueAsString(payload);
//        mvc.perform(post("/webshop/products/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                .with(jwt().authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    void authenticationProvider() {
    }

    @Test
    void authenticationManager() {
    }

    @Test
    void passwordEncoder() {
    }
}