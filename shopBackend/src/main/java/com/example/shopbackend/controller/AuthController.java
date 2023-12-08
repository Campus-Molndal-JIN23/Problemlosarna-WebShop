package com.example.shopbackend.controller;

import ch.qos.logback.core.model.Model;
import com.example.shopbackend.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/auth")
@RequiredArgsConstructor
public class AuthController {


    @PostMapping("/register")    //TODO  check if LoginForm fungerar from annan application
    public ResponseEntity<?> register (@RequestBody LoginForm loginForm){

        //TODO remove system out　
        System.out.println("username: " + loginForm.getUsername());
        System.out.println("password: " + loginForm.getPassword());

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login") //TODO  check if LoginForm fungerar  from annan application
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm ){
        //TODO remove system out
        System.out.println(loginForm.getPassword());
        System.out.println(loginForm.getUsername());

        //Vill retunera username och role
        return ResponseEntity.ok( "JWTToken"+"Admin"+"Username");

    }



}

