package com.example.shopfrontend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRespons {

    private long id;
    private String name;
    private int cost;
    private String description;
}
