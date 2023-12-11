package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);
}
