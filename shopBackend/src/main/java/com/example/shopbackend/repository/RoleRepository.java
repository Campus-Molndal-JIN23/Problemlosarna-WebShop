package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Integer> {

    Optional<Roles> findByAuthority(Enum authority);
}
