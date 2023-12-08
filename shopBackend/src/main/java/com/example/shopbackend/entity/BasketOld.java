package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<<< HEAD:shopBackend/src/main/java/com/example/shopbackend/entity/BasketOld.java
public class BasketOld {
========

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "baskets")
public class Basket {
>>>>>>>> dev:shopBackend/src/main/java/com/example/shopbackend/entity/Basket.java

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

<<<<<<<< HEAD:shopBackend/src/main/java/com/example/shopbackend/entity/BasketOld.java
    private HashMap<ProductOld, Integer> products;

    public BasketOld(int totalCost, HashMap<ProductOld, Integer> products) {
        this.totalCost = totalCost;
        this.products = products;
    }
========

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
>>>>>>>> dev:shopBackend/src/main/java/com/example/shopbackend/entity/Basket.java

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

<<<<<<<< HEAD:shopBackend/src/main/java/com/example/shopbackend/entity/BasketOld.java
    public HashMap<ProductOld, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<ProductOld, Integer> products) {
        this.products = products;
    }
========
>>>>>>>> dev:shopBackend/src/main/java/com/example/shopbackend/entity/Basket.java
}
