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
import java.util.Optional;

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

            var user1 = userRepository.save(new User("name1", "password"));
            var user2 = userRepository.save(new User("name2", "password"));

            var product1 = productRepository.save(new Product("Product 1", "Text about the product 1", 100));
            var product2 = productRepository.save(new Product("Product 2", "Text about the product 2", 200));
            var product3 = productRepository.save(new Product("Product 3", "Text about the product 3", 300));
            var product4 = productRepository.save(new Product("Product 4", "Text about the product 4", 400));

            var order1 = new Order(user1);
            var order2 = new Order(user2);

            var basket1 = new OrderQty(1, product1, 1, order1);
            var basket2 = new OrderQty(2, product2, 2, order1);
            var basket3 = new OrderQty(3, product3, 3, order1);
            var basket4 = new OrderQty(4, product4, 4, order1);

            var basket5 = new OrderQty(5, product1, 1, order2);
            var basket6 = new OrderQty(6, product2, 2, order2);


            order1.getOrderQty().add(basket1);
            order1.getOrderQty().add(basket2);
            order1.getOrderQty().add(basket3);
            order1.getOrderQty().add(basket4);

            order2.getOrderQty().add(basket5);
            order2.getOrderQty().add(basket6);

//            save orders
            orderRepository.save(order1);
            orderRepository.save(order2);


            orderQtyRepository.save(basket1);
            orderQtyRepository.save(basket2);
            orderQtyRepository.save(basket3);
            orderQtyRepository.save(basket4);
            orderQtyRepository.save(basket5);
            orderQtyRepository.save(basket6);


            // Check if the basket exsist

            Optional<Order> fetchOrder1 = orderRepository.findByUserId(user1.getId());
            System.out.println("Order 1 id: " + fetchOrder1.get().getId());

            List<OrderQty> orderQtyList1 = orderQtyRepository.findOrderQtyByOrderId(fetchOrder1.get().getId());
            for (OrderQty orderQty : orderQtyList1) {
                System.out.println("Order qty id:" + orderQty.getId());
            }



            Optional<Order> fetchOrder2 = orderRepository.findByUserId(user2.getId());
            System.out.println("Order 2 id:" + fetchOrder2.get().getId());

            List<OrderQty> orderQtyList2 = orderQtyRepository.findOrderQtyByOrderId(fetchOrder2.get().getId());
            for (OrderQty orderQty : orderQtyList2) {
                System.out.println("Order qty id:" + orderQty.getId());
            }
        };

    }

}
