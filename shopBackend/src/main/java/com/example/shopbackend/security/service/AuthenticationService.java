package com.example.shopbackend.security.service;

import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.security.dao.request.Request;
import com.example.shopbackend.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {


    JwtAuthenticationResponse signin(LoginForm loginForm);
}
