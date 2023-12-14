package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUserIdAndActiveBasket() {
        Long userID = 1L;

        var result = orderRepository.findByUserIdAndActiveBasket(userID, true);
        System.out.println(result);
    }

    @Test
    void createMultipleActiveOrders() {
        Long userId = 1L;

//        orderRepository.save(new Order(userRepository.findById(userId).get(), true));

        var result = orderRepository.findByUserIdAndActiveBasket(userId, true)
                .orElse(null);

        if (result == null) {
            orderRepository.save(new Order(userRepository.findById(userId).orElseThrow(() ->
                    new NoSuchElementException(String.valueOf(userId))),
                    true
            ));

        }
        System.out.println(result);

        var actual = orderRepository.findByUserIdAndActiveBasket(userId, true);
        System.out.println(actual);

    }


    @Test
    void getByUserIdAndActiveBasket() {
        Long userID = 1L;

        var result = orderRepository.getByUserIdAndActiveBasket(userID, true);
        System.out.println(result);


    }

    @Test
    void findByUserId() {
    }
}