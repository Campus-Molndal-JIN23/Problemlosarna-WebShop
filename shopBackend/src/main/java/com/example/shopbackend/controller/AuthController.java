package com.example.shopbackend.controller;


import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.LoginResponseDTO;
import com.example.shopbackend.security.ExtractData;
import com.example.shopbackend.security.service.AuthenticationService;
import com.example.shopbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/webshop/auth")
@RequiredArgsConstructor
public class AuthController {

    //TODO delete .body
    private final AuthenticationService authenticationService;
    private final AuthService authService;
    private final ExtractData extractData;

    @PostMapping("/register")    //TODO  check if LoginForm fungerar from annan application
    public ResponseEntity<?> register(@RequestBody LoginForm loginForm) {

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
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {

        try {
            Optional<User> userInfo = authService.getUserByUsername(loginForm);

            if (!userInfo.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User does not exist");
            }
            String token = authenticationService.signin(loginForm).getToken();

            Set<String> role = extractData.getUserRoles(token);
            LoginResponseDTO loginResponse = new LoginResponseDTO(loginForm.getUserName(), token, role.toString());

            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException ex) {
            // Handle BadCredentialsException and return an appropriate response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            // Handle other exceptions, if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    //or if dont exist
    //return ResponseEntity.status(404).body("User does not exist");
}





