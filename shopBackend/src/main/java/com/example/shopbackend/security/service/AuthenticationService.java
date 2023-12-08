package com.example.shopbackend.security.service;

import com.example.shopbackend.security.dao.request.Request;
import com.example.shopbackend.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {


    JwtAuthenticationResponse signin(Request request);
}
