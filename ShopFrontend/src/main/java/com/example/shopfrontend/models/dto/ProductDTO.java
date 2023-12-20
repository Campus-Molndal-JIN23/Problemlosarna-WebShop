package com.example.shopfrontend.models.dto;


import lombok.Data;

@Data
public class ProductDTO{

        Long id;
        String name;
        String description;
        Integer price;
        boolean deleted;

        public ProductDTO() {
        }

        public ProductDTO(Long id, String name, String description, Integer price, boolean deleted) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.deleted = deleted;
        }

}




