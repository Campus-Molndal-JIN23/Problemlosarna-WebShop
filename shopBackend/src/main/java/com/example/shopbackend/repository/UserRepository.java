package com.example.shopbackend.repository;

import com.example.shopbackend.entity.User;
import com.example.shopbackend.security.UserDetailsImpl;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetailsImpl>findByUserName(String userName);

}
