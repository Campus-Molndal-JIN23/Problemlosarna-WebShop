package com.example.shopbackend.controller;


import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.form.LoginResponseDTO;
import com.example.shopbackend.security.ExtractData;
import com.example.shopbackend.security.service.AuthenticationService;
import com.example.shopbackend.service.AuthService;
import com.example.shopbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/webshop/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AuthService authService;
    private final ExtractData extractData;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginForm loginForm) {

        if (!authService.isValidPassword(loginForm.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        if (userService.exists(loginForm).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        var user = authService.register(loginForm);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {

        try {
            Optional<User> userInfo = authService.getUserByUsername(loginForm);

            if (userInfo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            String token = authenticationService.signin(loginForm).getToken();

            Set<String> role = extractData.getUserRoles(token);
            LoginResponseDTO loginResponse = new LoginResponseDTO(loginForm.getUserName(), token, role.toString());
            log.info("login ok");
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException ex) {
            log.info("login attempt, bad credentials");
            // Handle BadCredentialsException and return an appropriate response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            // Handle other exceptions, if needed
            log.error("Login failed " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //or if dont exist
    //return ResponseEntity.status(404).body("User does not exist");
}





