package com.example.shopbackend.security.service;

import com.example.shopbackend.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface JwtService {

    String extractUserName(String token);

    Long extractId(String token);

    String generateToken(UserDetailsImpl userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    Set<String> extractRoles(String token);
}
