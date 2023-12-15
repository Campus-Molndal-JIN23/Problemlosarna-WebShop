package com.example.shopbackend.service;

import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;


/**
 * This is the UserService only contains one metod and could be lambda?
 */

@Service
public class GetUser {

    UserRepository userRepository;

    public GetUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserId(Principal principal) {
        var  user = userRepository.findUserByUserName(principal.getName()).orElse(null);
        return user == null ? null : user.getId();
    }
}
