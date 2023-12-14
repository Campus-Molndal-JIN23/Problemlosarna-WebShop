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

        OrderQty addItem = new OrderQty();

        // make sure the product exists
        addItem.setProduct(productRepository.findById(payload.productId())
                .orElse(null));
        if (addItem.getProduct() == null) {
            return null;
        }

        if (payload.quantity() > 0) {
            addItem.setQuantity(payload.quantity());
        } else {
            return null;
        }
        // get the active basket if not found create one
        addItem.setOrder(orderRepository.findByUserIdAndActiveBasket(userId, true)
                .orElse(null));
        if (addItem.getOrder() == null) {
            addItem.setOrder(orderRepository.save(new Order(userRepository.findById(userId).orElseThrow(() ->
                    new NoSuchElementException(String.valueOf(userId))),
                    true
            )));

        }
        return orderQtyRepository.save(addItem);
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
