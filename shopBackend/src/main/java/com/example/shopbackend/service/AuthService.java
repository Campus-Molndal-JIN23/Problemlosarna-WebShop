package com.example.shopbackend.service;

import com.example.shopbackend.entity.Role;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

      private final UserRepository userRepository;
      private final Mapper mapper;
      private final PasswordEncoder passwordEncoder;
      public Optional<User> register(LoginForm loginForm){

            var userInfo = mapper.map(loginForm, User.class);
            var encodedPassword = passwordEncoder.encode(loginForm.getPassword());
            userInfo.setPassword(encodedPassword);
            return Optional.of(userRepository.save(userInfo));
      }

      public Optional<User> getUserByUsername(LoginForm loginForm){
            String userName = loginForm.getUsername();
           return userRepository.findUserByUserName(userName)
                   .or(()-> Optional.empty());
      }

      public boolean isValidPassword(String password){
            return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
      }

}
