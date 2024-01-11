package com.example.shopbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/webshop/user")
public class UserController {


    @GetMapping("")
    public ResponseEntity<Principal> userDetails(Principal principal) {
       log.info(principal.toString());
        return ResponseEntity.ok(principal);
    }
}
