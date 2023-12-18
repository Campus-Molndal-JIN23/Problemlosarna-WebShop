package com.example.shopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("webshop/user")
public class UserController {

    @GetMapping("")
    public ResponseEntity<?> userDetails(Principal principal) {
        System.out.println(principal.toString());
        return ResponseEntity.ok(principal);
    }
}
