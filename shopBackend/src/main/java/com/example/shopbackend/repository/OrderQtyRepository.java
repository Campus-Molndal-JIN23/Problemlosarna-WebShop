package com.example.shopbackend.repository;

import com.example.shopbackend.entity.OrderQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderQtyRepository extends JpaRepository<OrderQty, Long> {

    List<OrderQty> findOrderQtyByOrderId(Long userId);

    Optional<OrderQty> findOrderQtyByOrder_IdAndProductId(Long orderId, Long productId);
    Optional<OrderQty> findByOrder_IdAndProductId(Long orderId, Long productId);



    void deleteOrderQtyByOrder_IdAndProductId(Long orderId, Long productId);


}
