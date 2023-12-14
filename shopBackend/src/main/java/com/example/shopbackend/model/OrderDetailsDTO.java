package com.example.shopbackend.model;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.*;


@Data
public class OrderDetailsDTO {


   // private HashMap<String,Object> orderList =new HashMap<>();
   private List <UserNameAndOrders> allOrders;

    public OrderDetailsDTO(HashMap<User,List<List<OrderQty>>> allUsersAndOrders) {
        List<BasketDTO> orders;
        allOrders = new ArrayList<>();

        for(Map.Entry<User,List<List<OrderQty>>>entry : allUsersAndOrders.entrySet() ){
            orders =new ArrayList<>();
            User user = entry.getKey();
            List<List<OrderQty>> value = entry.getValue();
              for(List<OrderQty>orderQtyList : value){
                  orders.add(new BasketDTO(orderQtyList));
              }
            allOrders.add(new UserNameAndOrders(user.getUserName(),orders));
          }
    }


    @Data
    public class UserNameAndOrders implements Serializable {

        String name;
        List<BasketDTO>orders = new ArrayList<>();
        public UserNameAndOrders(String name,List<BasketDTO>orders){

            this.name = name;
            this.orders = orders;

        }

    }

}


