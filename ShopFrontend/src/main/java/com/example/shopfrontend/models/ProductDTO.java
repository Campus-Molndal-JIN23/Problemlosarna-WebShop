package com.example.shopfrontend.models;


import lombok.Data;

@Data
public class ProductDTO{

        Long id;
        String name;
        String description;
        Integer price;

        public ProductDTO() {
        }

        public ProductDTO(Long id, String name, String description, Integer price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
        }

}




