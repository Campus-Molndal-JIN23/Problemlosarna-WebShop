package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.model.BasketProductDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {

    private final OrderRepository orderRepository;
    private final OrderQtyRepository orderQtyRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;


    public BasketService(OrderRepository orderRepository, OrderQtyRepository orderQtyRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderQtyRepository = orderQtyRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public BasketDTO getBasket(Long userId) {

        Optional<Order> order = orderRepository.findByUserIdAndActiveBasket(userId, true);

        return order.map(value -> new BasketDTO(orderQtyRepository.findOrderQtyByOrderId(value.getId()))).orElse(null);
    }

    public Boolean addProduct(Long userID, BasketProductDTO payload) {

        var product = productRepository.findById(payload.getId());
        int quantity = payload.getQuantity();


        Long basketId = orderRepository.findByUserIdAndActiveBasket(userID, true)
                .orElse(orderRepository.save(new Order(userRepository.findById(userID).orElseThrow(), true))
                .getId());

        var reslut = orderQtyRepository.save()

        return null;
    }

}
