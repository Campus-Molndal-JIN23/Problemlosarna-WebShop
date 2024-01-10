package com.example.shopbackend.service;

import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


/**
 * This is the UserService only contains one metod and could be lambda?
 */

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserId(Principal principal) {
        var  user = userRepository.findUserByUserName(principal.getName()).orElse(null);
        return user == null ? null : user.getId();
    }

    public Optional<User> exists(LoginForm loginForm) {
        // todo find user in db and return
        return userRepository.findUserByUserName(loginForm.getUserName());


    }

}
