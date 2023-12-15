package com.example.shopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("webshop/user")
public class UserController {

    /* /webshop/
GET /user/:id

Returns the specified user.

    URL Params
    Data Params
    Headers
    Content-Type: application/json
    Authorization: Bearer <OAuth Token>
    Success Response:
    Code: 200
    Content: { <name och lösenord i objekt> }
    Error Response:
        Code: 404
        Content: { error : "User doesn't exist" }
        OR
        Code: 401
        Content: { error : error : "You are unauthorized to make this request." }
     */

    @GetMapping("/")
    public ResponseEntity<?> userDetails(Principal principal) {

        principal.getName();


        return null;
    }
}
