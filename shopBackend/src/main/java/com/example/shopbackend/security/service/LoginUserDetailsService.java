package com.example.shopbackend.security.service;

import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserService {

    private final UserRepository repository; //TODO check Repository Name

    /**
     * Retrieves user details based on the provided email.
     * Used by Spring Security for authentication and authorization.
     * @return UserDetailsService that loads user details from the repository.
     */
    @Override
    public UserDetailsService userDetailsService() {
        // Lambda expression defining a UserDetailsService for the given email
        return userName -> {
            // Retrieve a user from the repository based on the provided email
            UserDetailsImpl user1 = repository.findByUserName(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Build and return a UserDetails object with the user's information
            return User.builder()
                    .username(user1.getUsername())
                    .password(user1.getPassword())
                    .authorities(user1.getAuthorities()) // Set authorities (roles) appropriately
                    .build();
        };
    }
}
