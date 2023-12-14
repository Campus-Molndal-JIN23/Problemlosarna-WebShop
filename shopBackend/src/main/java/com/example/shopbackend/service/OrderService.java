package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    private final OrderQtyRepository orderQtyRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderQtyRepository orderQtyRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderQtyRepository = orderQtyRepository;
    }

    public OrderDTO findAllUserOrders(Long userId) {
        List<List<OrderQty>> baskets = new ArrayList<>();

        // orders are always past baskets and set to false
        List<Order> orders = orderRepository.getByUserIdAndActiveBasket(userId, false).orElse(null);

        if (orders == null) {
            return null;
        } else {
            for (Order order : orders) {
                baskets.add(orderQtyRepository.findOrderQtyByOrderId(order.getId()));
            }
            return new OrderDTO(orders, baskets);
        }
    }



    public List <User> findAllUsers(){
        return  userRepository.findAll();
    }

    public OrderDetailsDTO findAllOrders(){
        HashMap<User,List<List<OrderQty>>> allUsersAndOrders = new HashMap<>();
        List<List<OrderQty>> baskets = new ArrayList<>();
        List<User> users = findAllUsers();
        for(User user : users){
            System.out.println(user.getId());
            List<Order> orders = orderRepository.getByUserIdAndActiveBasket(user.getId(), false).orElse(null);
            System.out.println(orders);
            if(orders == null){
                continue;
            }

            for(Order order : orders) {
                baskets.add(orderQtyRepository.findOrderQtyByOrderId(order.getId()));

            }

            allUsersAndOrders.put(user,baskets);

        }
            return new OrderDetailsDTO(allUsersAndOrders);

    }

}
