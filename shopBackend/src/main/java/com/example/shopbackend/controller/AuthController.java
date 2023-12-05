package com.example.shopbackend.controller;

import ch.qos.logback.core.model.Model;
import com.example.shopbackend.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/register")
    public String registerView(Model model, LoginForm loginForm){


        return "register"; //Todo change to actual html name
    }

    @PostMapping("/register")
    public void register(Model model, LoginForm loginForm){

        System.out.println("Username: " + loginForm.getUsername());
        System.out.println("Email: " + loginForm.getPassword());

    }


    @GetMapping("/login")
    public String loginView(Model model,LoginForm loginForm){

        return "login"; //Todo change to actual html name
    }

    @PostMapping("/login")
    public void login(Model model,LoginForm loginForm ){

        System.out.println(loginForm.getPassword());
        System.out.println(loginForm.getUsername());

        System.out.println("JWT token genererad");

    }



}

