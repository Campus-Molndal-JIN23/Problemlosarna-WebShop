package com.example.shopfrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private long id;
    private String name;
    private int cost;
    private String description;

}
