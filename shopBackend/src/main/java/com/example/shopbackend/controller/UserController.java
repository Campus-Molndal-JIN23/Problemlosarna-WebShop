package com.example.shopbackend.controller;

import com.example.shopbackend.model.UserDTO;
import com.example.shopbackend.security.ExtractData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("webshop/user")
public class UserController {

    private final ExtractData extractData;

    public UserController(ExtractData extractData) {
        this.extractData = extractData;
    }

    @GetMapping("")
    public ResponseEntity<?> userDetails(@RequestBody String jwt) {
        return ResponseEntity.ok(new UserDTO(extractData.getUserID(jwt), extractData.getUserName(jwt), extractData.getUserName(jwt)));
    }
}
