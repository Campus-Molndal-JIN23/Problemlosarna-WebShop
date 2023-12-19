package com.example.shopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
=======
>>>>>>> 26206c5a80669ef979951bf1d511ff712522b763
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/webshop/user")
public class UserController {


    @GetMapping("")
    public ResponseEntity<?> userDetails(Principal principal) {
        System.out.println(principal.toString());
        return ResponseEntity.ok(principal);
    }
}
