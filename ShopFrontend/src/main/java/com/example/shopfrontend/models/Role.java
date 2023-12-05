package com.example.shopfrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    //TODO vi kanske inte behöver den här klassen

    private Long roleId;
    private String authority;


}
