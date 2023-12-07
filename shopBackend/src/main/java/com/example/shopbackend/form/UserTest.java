package com.example.shopbackend.form;


import com.example.shopbackend.entity.OrderOld;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

//TODO remove this class

@Data
@AllArgsConstructor
public class UserTest {

    private String username;
    private List<OrderOld> orders;


}
