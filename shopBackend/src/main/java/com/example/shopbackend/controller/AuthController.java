package com.example.shopbackend.controller;

import ch.qos.logback.core.model.Model;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.LoginResponseDTO;
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

        return ResponseEntity.ok("User created");
        //or if user already exists this
        //return ResponseEntity.status(409).body("User already exists");
    }


    @PostMapping("/login") //TODO  check if LoginForm fungerar  from annan application
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginForm loginForm ){
        //TODO remove system out
        System.out.println(loginForm.getPassword());
        System.out.println(loginForm.getUsername());
        LoginResponseDTO loginResponse=new LoginResponseDTO("userName","Token","ROLE_ADMIN");
        //Vill retunera username och role
        return ResponseEntity.ok(loginResponse);
        //or if dont exist
        //return ResponseEntity.status(404).body("User does not exist");


    }



}

