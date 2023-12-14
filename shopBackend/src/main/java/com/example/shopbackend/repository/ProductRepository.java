package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByDeleted(Boolean isDeleted);

    Optional<Product> findByIdAndDeleted(Long productId, Boolean isDeleted);
}
