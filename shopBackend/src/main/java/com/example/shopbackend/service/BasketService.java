package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.form.UpdateBasketDTO;
import com.example.shopbackend.model.BasketDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public OrderQty addProduct(Long userId, UpdateBasketDTO payload) {

        // get the active basket if not found create one
        Order order = orderRepository.findByUserIdAndActiveBasket(userId, true)
                .orElse(null);
        if (order == null)
            order = orderRepository.save(new Order(userRepository.findById(userId).orElseThrow(() ->
                    new NoSuchElementException(String.valueOf(userId))),
                    true
            ));

        // make sure the product exists and in use
        Product product = productExists(payload);
        if (product == null) return null;

        if (!validQuantity(payload)) return null;

        return orderQtyRepository.save(new OrderQty(product, payload.quantity(), order));
    }

    public OrderQty updateQuantityProduct(Long userId, UpdateBasketDTO payload) {

        // get the active basket if not found return
        Order order = orderRepository.findByUserIdAndActiveBasket(userId, true)
                .orElse(null);
        if (order == null) return null;

        // get the object that needs updating from the table
        OrderQty basket = orderQtyRepository.findOrderQtyByOrder_IdAndProductId(order.getId(), payload.productId());

        // make sure the product exists and in use
        if (productExists(payload) == null) return null;

        if (!validQuantity(payload)) return null;

        basket.setQuantity(payload.quantity());

        return orderQtyRepository.save(basket);
    }

    private Boolean validQuantity(UpdateBasketDTO payload) {
        return payload.quantity() > 0;
    }

    private Product productExists(UpdateBasketDTO payload) {
        return productRepository.findByIdAndDeleted(payload.productId(), false)
                .orElse(null);
    }

    @Transactional
    public boolean deleteProductActiveBasket(Long userId, UpdateBasketDTO payload) {
        // find basket
        var basket = orderRepository.findByUserIdAndActiveBasket(userId, true);

        // find product and delete
        if (basket.isPresent()) {
            orderQtyRepository.deleteOrderQtyByOrder_IdAndProductId(basket.get().getId(), payload.productId());
            return true;
        }
        return false;
    }
}
