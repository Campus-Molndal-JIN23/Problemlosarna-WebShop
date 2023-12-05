package com.example.shopfrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //TODO vi kanske inte behöver den här klassen


    Long id;

    String username;

    String jwt;

    Set<Role> roles;
}
