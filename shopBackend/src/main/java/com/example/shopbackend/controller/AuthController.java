package com.example.shopbackend.controller;

import ch.qos.logback.core.model.Model;
import com.example.shopbackend.entity.Roles;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.LoginResponseDTO;

import com.example.shopbackend.security.ExtractData;
import com.example.shopbackend.security.service.AuthenticationService;
import com.example.shopbackend.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/webshop/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
   private final AuthService authService;
   private final ExtractData extractData;
    @PostMapping("/register")    //TODO  check if LoginForm fungerar from annan application
    public ResponseEntity<?> register (@RequestBody LoginForm loginForm){

        if (authService.getUserByUsername(loginForm).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        if (!authService.isValidPassword(loginForm.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        try {
            authService.register(loginForm);
            return ResponseEntity.ok("User created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }

        //or if user already exists this
        //return ResponseEntity.status(409).body("User already exists");
    }


    @PostMapping("/login") //TODO  check if LoginForm fungerar  from annan application
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm ){

        Optional<User> userInfo = authService.getUserByUsername(loginForm);
        System.out.println(userInfo+"userInfo");

        //if(!userInfo.isPresent())
          // return ResponseEntity.status(HttpStatus.CONFLICT).body("User does not exists");
        String token = authenticationService.signin(loginForm).getToken();
        System.out.println(authenticationService.signin(loginForm));
        System.out.println(token);
        Set<String> role= extractData.getUserAgent(token);
        LoginResponseDTO loginResponse=new LoginResponseDTO(loginForm.getUserName(),"token", role.toString());
        //Vill retunera username och role
        return ResponseEntity.ok(loginResponse);
        //or if dont exist
        //return ResponseEntity.status(404).body("User does not exist");


    }



}

