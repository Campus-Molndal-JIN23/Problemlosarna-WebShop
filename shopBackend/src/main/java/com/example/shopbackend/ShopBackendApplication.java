package com.example.shopbackend;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.OrderQty;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // Exclude to make sure the project runs for all.
//@SpringBootApplication
public class ShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBackendApplication.class, args);
    }

    /**
     * This is intended to work with the H2 database in memory when switching to a permanent storage this needs refactoring
     *
     * @param orderQtyRepository
     * @param orderRepository
     * @param productRepository
     * @param userRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, ObjectMapper mapper) {
        return args -> {
/*
            var user1 = userRepository.save(new User("name1", "password"));
            var user2 = userRepository.save(new User("name2", "password"));

            var product1 = productRepository.save(new Product("Product 1", "Text about the product 1", 100));
            var product2 = productRepository.save(new Product("Product 2", "Text about the product 2", 200));
            var product3 = productRepository.save(new Product("Product 3", "Text about the product 3", 300));
            var product4 = productRepository.save(new Product("Product 4", "Text about the product 4", 400));

            var order1 = new Order(user1);
            var order2 = new Order(user2);

            var basket1 = new OrderQty(1, product1, 1, order1);
//            var basket2 = new Basket(2,user1, product2, 2, order1);
//            var basket3 = new Basket(3,user1, product3, 3, order1);
//            var basket4 = new Basket(4,user1, product4, 4, order1);
            orderQtyRepository.save(basket1);

            order1.getOrderQty().add(basket1);
//            order1.getBasket().add(basket2);
//            order1.getBasket().add(basket3);
//            order1.getBasket().add(basket4);

            orderRepository.save(order1);

            var basket5 = new OrderQty(product1, 1, order2);
            var basket6 = new OrderQty(product2, 2, order2);

            order2.getOrderQty().add(basket5);
            order2.getOrderQty().add(basket6);

            orderRepository.save(order2);

            // Check if the basket exsist

            List<OrderQty> orderQtyList1 = orderQtyRepository.findAllByUserId(1L).orElse(null);

            if (orderQtyList1 != null) {
                System.out.println(mapper.writeValueAsString(orderQtyList1));
            }

            List<OrderQty> orderQtyList2 = orderQtyRepository.findAllByUserId(2L).orElse(null);

            if (orderQtyList2 != null) {
                System.out.println(mapper.writeValueAsString(orderQtyList2));
            }
            */

        };

    }

}
