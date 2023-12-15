package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserIdAndActiveBasket(Long id, Boolean active);

    Optional<List<Order>> getByUserIdAndActiveBasket(Long id, Boolean active);

    Optional<List<Order>> getAllByActiveBasket(Boolean isBasket);

    Optional<List<Order>> findByUserId(Long id);







}
