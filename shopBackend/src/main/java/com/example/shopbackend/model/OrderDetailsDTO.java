package com.example.shopbackend.model;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Data
public class OrderDetailsDTO {


    private List<HashMap<String,Object>> orderList;



    public OrderDetailsDTO(List<User> users, List<List<OrderQty>> orders) {

        for(User user : users ){

           orders.get(user);

        }


    }


}
