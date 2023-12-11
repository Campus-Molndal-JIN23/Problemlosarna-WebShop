package com.example.shopbackend.security.service;

import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.security.dao.response.JwtAuthenticationResponse;

import java.util.Optional;

public interface AuthenticationService {



    JwtAuthenticationResponse signin(LoginForm request);

    JwtAuthenticationResponse signin(User request);
}
