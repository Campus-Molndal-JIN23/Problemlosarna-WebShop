package com.example.shopbackend.controller;

import com.example.shopbackend.entity.Role;
import com.example.shopbackend.entity.Roles;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.LoginResponseDTO;
import com.example.shopbackend.repository.RoleRepository;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.service.AuthService;
import com.example.shopbackend.service.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.lang.reflect.*;
import java.security.Key;
import java.util.*;
import sun.misc.Unsafe;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    ObjectMapper mapper = new ObjectMapper();
    LoginForm loginForm;
    String token;
    private String userName;
    private String password;
    private String role;

    User user;
    @Mock
    private UserRepository userRepository;


    @Mock
    UserRepository mockUserService;

    @Mock
    RoleRepository mockUserRoleRepository;

    @Mock
    BasketService mockBasketService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    AuthService mockAuthService ;

    @Autowired
    MockMvc mvc;
    @BeforeEach
    void setUp() throws Exception {
        userName = "user";
        password = "Password1";
        loginForm = new LoginForm(userName,password);
        role = "ROLE_USER";
        Roles roles = new Roles(Role.ROLE_USER);

        String jwtSigningKey = "0b3a2839ae978330c29d866068171086524b7cd5a7e8a7ddc5fd7a893f7caf7b";
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", Collections.singleton(role));
        claims.put("userID", 1L);
        claims.put("username", userName);

        token = Jwts.builder()
                .setClaims(claims)
                .setSubject("USER")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        MockitoAnnotations.openMocks(this);

        user =User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(loginForm.getPassword()))
                .roles(new HashSet<>(Collections.singletonList(roles))).build();

        initMocks(this);
    }


    @Test
    void register() throws Exception {

        String API = "/webshop/auth/register";
        LoginForm loginForm = new LoginForm("userName","Password1");
        String json = mapper.writeValueAsString(loginForm);

        this.mvc.perform(post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }



}