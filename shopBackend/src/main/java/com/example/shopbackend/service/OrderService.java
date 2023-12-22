package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.model.OrderDTO;
import com.example.shopbackend.model.OrderDetailsDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderQtyRepository orderQtyRepository;
    private final BasketService basketService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderQtyRepository orderQtyRepository, BasketService basketService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderQtyRepository = orderQtyRepository;
        this.basketService = basketService;
    }

    public OrderDTO findAllUserOrders(Long userId) {
        List<List<OrderQty>> baskets = new ArrayList<>();

        // orders are always past baskets and set to false
        try {
            List<Order> orders = orderRepository.getByUserIdAndActiveBasket(userId, false).orElse(null);
            assert orders != null;
            for (Order order : orders) {
                baskets.add(orderQtyRepository.findOrderQtyByOrderId(order.getId()));
            }
            return new OrderDTO(orders, baskets);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public OrderDTO placeOrder(Long userId) {

        Optional<Order> orderOptional = orderRepository.findByUserIdAndActiveBasket(userId, true);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setActiveBasket(false);
            orderRepository.save(order);
            basketService.createBasket(userId); // create a new basket
            return new OrderDTO(order, orderQtyRepository.findOrderQtyByOrderId(order.getId()));
        } else {
            return null;
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public OrderDetailsDTO findAllOrders() {

        List<Order> orders;
        List<List<OrderQty>> baskets;
        HashMap<User, List<List<OrderQty>>> allUsersAndOrders = new HashMap<>();
        List<User> users = findAllUsers();

        for (User user : users) {
            baskets = new ArrayList<>();
            orders = orderRepository.getByUserIdAndActiveBasket(user.getId(), false).orElse(null);
            if (orders.isEmpty()) {
                continue;
            }
            for (Order order : orders) {
                baskets.add(orderQtyRepository.findOrderQtyByOrderId(order.getId()));
            }
            allUsersAndOrders.put(user, baskets);
        }
        return new OrderDetailsDTO(allUsersAndOrders);
    }
}
