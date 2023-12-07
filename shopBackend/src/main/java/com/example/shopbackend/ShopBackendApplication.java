package com.example.shopbackend;

import com.example.shopbackend.entity.Basket;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.repository.BasketRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.UserRepository;
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
     * This is intended to work with the H2 database in memory when swithcing to a permanent storage this needs refactoring
     *
     * @param basketRepository
     * @param orderRepository
     * @param productRepository
     * @param userRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(BasketRepository basketRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        return args -> {
/*
            var user1 = userRepository.save(new User("name1", "password"));
//            var user2 = userRepository.save(new User("name2", "password"));

            var product1 = productRepository.save(new Product("Product 1", "Text about the product 1", 100));
            var product2 = productRepository.save(new Product("Product 2", "Text about the product 2", 200));
            var product3 = productRepository.save(new Product("Product 3", "Text about the product 3", 300));
            var product4 = productRepository.save(new Product("Product 4", "Text about the product 4", 400));

            var newOrder = orderRepository.save(new Order(user1));

            basketRepository.save(new Basket(user1, product1, 1, newOrder));



            basketRepository.save(new Basket(user1, product2, 2, newOrder));
            basketRepository.save(new Basket(user1, product3, 3, newOrder));
            basketRepository.save(new Basket(user1, product4, 4, newOrder));

            List<Basket> basket1 = basketRepository.findAllByUserId(1L).orElse(null);
*/

//            var basket2 = basketRepository.save(new Basket(user2));


//            var order1 = orderRepository.save(user1, basket1);

//            var order3 = orderRepository.save(user2, basket2);


        };
    }

}
