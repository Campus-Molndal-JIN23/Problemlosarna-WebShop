package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderQty> OrderQty = new ArrayList<>();

    @Column(name = "is_basket", nullable = false)
    private boolean activeBasket;

    /**
     * This constructor is for creating a mock database
     *
     * @param user
     * @param activeBasket
     */

    public Order(User user, boolean activeBasket) {
        this.user = user;
        this.activeBasket = activeBasket;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
