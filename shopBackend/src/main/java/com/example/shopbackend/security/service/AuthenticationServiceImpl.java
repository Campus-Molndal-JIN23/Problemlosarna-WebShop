package com.example.shopbackend.security.service;

import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.repository.RoleRepository;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.security.dao.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;  //TODO check Repository Name
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Authenticates a user based on the provided sign-in request.
     * Uses Spring Security's authentication manager to validate credentials.
     * Generates a JWT token upon successful authentication.
     *
     * @param request SigningRequest object containing user sign-in details.
     * @return JwtAuthenticationResponse with the generated JWT token.
     * @throws IllegalArgumentException if the provided email or password is invalid.
     */
    @Override
    public JwtAuthenticationResponse signin(LoginForm request) {
        System.out.println(request.getUserName()+":jwtAuthenticationResponse");

        // Authenticate the user using Spring Security's authentication manager
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        // Retrieve the authenticated user from the repository
        var user = userRepository.findUserByUserName(request.getUserName()) //TODO ask to make findByUsername
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        // Generate a JWT token for the authenticated user
        var jwt = jwtService.generateToken(user);
        // Return the generated JWT token in the response
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
