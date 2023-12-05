package com.example.shopbackend.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Order {

    long id;
    Basket Basket;

}
