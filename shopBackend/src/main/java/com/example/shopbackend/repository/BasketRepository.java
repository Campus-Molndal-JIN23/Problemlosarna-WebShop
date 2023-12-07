package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<List<Basket>> findAllByUserId(Long id);


}
