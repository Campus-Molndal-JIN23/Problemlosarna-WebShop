package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.form.UpdateBasketDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

@ActiveProfiles("test")
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderQtyRepository orderQtyRepository;

    @Test
    void findByUserIdAndActiveBasket() {
        Long userID = 1L;

        var result = orderRepository.findByUserIdAndActiveBasket(userID, true);
        System.out.println(result);
    }

    @Test
    void FindActiveBasketForUser() {
        Long userId = 1L;
        var payload = new UpdateBasketDTO(2L, 999);

        Order order = orderRepository.findByUserIdAndActiveBasket(userId, true)
                .orElse(null);
        System.out.println(order);
        // get the object that needs updating from the table
        var basket = orderQtyRepository.findByOrder_IdAndProductId(order.getId(), payload.productId()).orElse(null);
        System.out.println(basket);

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