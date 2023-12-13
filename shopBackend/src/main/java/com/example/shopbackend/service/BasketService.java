package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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

    public OrderQty addProduct(Long userID, UpdateBasketDTO payload) {

        OrderQty addItem = new OrderQty();

        try {
            addItem.setProduct(productRepository.findById(payload.productId())
                    .orElseThrow(() ->
                            new NoSuchElementException(String.valueOf(payload.productId()))));

            addItem.setQuantity(payload.quantity());

            addItem.setOrder(orderRepository.findByUserIdAndActiveBasket(userID, true)
                    .orElseThrow(() ->
                            new NoSuchElementException(String.valueOf(userID))));

        } catch (Exception e) {
            return null;
        }
        return orderQtyRepository.save(addItem);
    }

}
