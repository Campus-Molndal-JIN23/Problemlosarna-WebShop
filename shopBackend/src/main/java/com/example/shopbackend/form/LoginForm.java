package com.example.shopbackend.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    /**  login id*/
    String username;
    /**  password */
    String password;

}
