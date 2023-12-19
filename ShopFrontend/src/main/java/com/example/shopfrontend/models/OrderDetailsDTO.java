package com.example.shopfrontend.models;


import lombok.Data;


import java.util.List;


@Data
public class OrderDetailsDTO {

   private List<UserNameAndOrders> allOrders;

    public OrderDetailsDTO(List<UserNameAndOrders> allOrders) {
        this.allOrders = allOrders;
    }

    public OrderDetailsDTO() {
    }

    @Data
    public static class UserNameAndOrders {

        String name;
        List<BasketDTO>orders;

        public UserNameAndOrders(String name,List<BasketDTO>orders){
            this.name = name;
            this.orders = orders;

        }

        public UserNameAndOrders() {
        }

    }

}


