package com.example.shopbackend.service;

import com.example.shopbackend.entity.Role;
import com.example.shopbackend.entity.Roles;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.repository.RoleRepository;
import com.example.shopbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

      private final UserRepository userRepository;
      private final Mapper mapper;
      private final PasswordEncoder passwordEncoder;
      private final RoleRepository roleRepository;
      public Optional<User> register(LoginForm loginForm){
            Roles role = roleRepository.findByAuthority(Role.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Roles(Role.ROLE_USER)));
            var userInfo = User.builder()
                    .userName(loginForm.getUserName())
                    .roles(new HashSet<>(Collections.singletonList(role)))
                    .password(passwordEncoder.encode(loginForm.getPassword()))
                    .build();
            return Optional.of(userRepository.save(userInfo));
      }

      public Optional<User> getUserByUsername(LoginForm loginForm){
            String userName = loginForm.getUserName();
           return userRepository.findUserByUserName(userName)
                   .or(()-> Optional.empty());
      }

      public boolean isValidPassword(String password){

            return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*");
      }

}
