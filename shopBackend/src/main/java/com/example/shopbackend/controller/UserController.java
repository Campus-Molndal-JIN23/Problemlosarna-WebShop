package com.example.shopbackend.controller;

import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.model.UserDTO;
import com.example.shopbackend.security.ExtractData;
import com.example.shopbackend.service.GetUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("webshop/user")
public class UserController {

    @GetMapping("")
    public ResponseEntity<?> userDetails(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
