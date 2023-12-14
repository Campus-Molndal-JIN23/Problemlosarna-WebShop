package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final BasketService basketService;
    private final OrderQtyRepository orderQtyRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderQtyRepository orderQtyRepository,BasketService basketService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderQtyRepository = orderQtyRepository;
        this.basketService=basketService;
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

    public Object placeOrder(Long userId){

        Optional<Order> order = orderRepository.findByUserIdAndActiveBasket(userId, true);
            order.get().setActiveBasket(false);
             orderRepository.save(order.get());

        return new OrderDTO(order.get(), orderQtyRepository.findOrderQtyByOrderId(order.get().getId()));

    }




    public List <User> findAllUsers(){
        return  userRepository.findAll();
    }

    public OrderDetailsDTO findAllOrders(){

        List<Order> orders;
        List<List<OrderQty>> baskets;
        HashMap<User,List<List<OrderQty>>> allUsersAndOrders = new HashMap<>();
        List<User> users = findAllUsers();

        for(User user : users){
            baskets = new ArrayList<>();
            orders = orderRepository.getByUserIdAndActiveBasket(user.getId(), false).orElse(null);
            if(orders.isEmpty()){
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
