package com.example.shopbackend.repository;

import com.example.shopbackend.entity.OrderQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderQtyRepository extends JpaRepository<OrderQty, Long> {

    List<OrderQty> findOrderQtyByOrderId(Long userId);

    void deleteByOrder_IdAndProductId(Long orderId, Long productId);

    void deleteOrderQtyByOrder_IdAndProductId(Long orderId, Long productId);
}
