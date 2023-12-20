package com.example.shopfrontend.models.userDetails;

import com.example.shopfrontend.models.userDetails.Authority;
import com.example.shopfrontend.models.userDetails.Details;
import com.example.shopfrontend.models.userDetails.Principal;
import lombok.Data;


import java.util.List;


@Data
public class UserDTO {
    private String name;
    private boolean authenticated;
    private Principal principal;
    private Details details;
    private String password;
    private String credentials;
    private List<Authority> authorities;
}
