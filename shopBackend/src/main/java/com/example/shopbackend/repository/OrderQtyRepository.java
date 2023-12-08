package com.example.shopbackend.repository;

import com.example.shopbackend.entity.OrderQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderQtyRepository extends JpaRepository<OrderQty, Long> {

}
