package com.example.shopbackend.security;

import com.example.shopbackend.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExtractData {
    private final JwtService jwtservice;
    private final HttpServletRequest request;

    public Set<String> getUserAgent(){
        String jwt = request.getHeader("Authorization").substring(7);

        return jwtservice.extractRoles(jwt);
    }

    public Set<String> getUserRoles(String jwt){

        return jwtservice.extractRoles(jwt);
    }

    public Long getUserID(String jwt){

        return jwtservice.extractId(jwt);

    }

    public String getUserName(String jwt){

        return jwtservice.extractUserName(jwt);

    }
}
