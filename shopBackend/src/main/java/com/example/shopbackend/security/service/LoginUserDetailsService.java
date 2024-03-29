package com.example.shopbackend.security.service;


import com.example.shopbackend.repository.UserRepository;
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
        // Lambda expression defining a UserDetailsService for the given userName
        return userName -> {
            // Retrieve a user from the repository based on the provided userName
            com.example.shopbackend.entity.User user = repository.findUserByUserName(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Build and return a UserDetails object with the user's information
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getAuthorities()) // Set authorities (roles) appropriately
                    .build();
        };
    }
}
