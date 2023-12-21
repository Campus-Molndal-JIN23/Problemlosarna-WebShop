package com.example.shopbackend;

import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.Role;
import com.example.shopbackend.entity.Roles;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.repository.ProductRepository;
import com.example.shopbackend.repository.RoleRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // Exclude to make sure the project runs for all.
//@SpringBootApplication
public class ShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBackendApplication.class, args);
    }

    private static void createUser(String userName, UserRepository userRepository, PasswordEncoder passwordEncoder, Roles role) {
        var user = User.builder()
                .userName(userName)
                .roles(new HashSet<>(Collections.singletonList(role)))
                .password(passwordEncoder.encode("Password1"))
                .build();
        userRepository.save(user);
    }

    private static void addProducts(ProductRepository productRepository) {
        productRepository.save(new Product("Product 1", "Text about the product 1", 100));
        productRepository.save(new Product("Product 3", "Text about the product 3", 300));
        productRepository.save(new Product("Product 4", "Text about the product 4", 400));
        productRepository.save(new Product("Product 2", "Text about the product 2", 200));
        productRepository.save(new Product("product in order history 1", "Text about..", 55));
        productRepository.save(new Product("product in order history 2", "Text about..", 66));
        productRepository.save(new Product("One to delete in test", "Text about..", 365));
    }

    /**
     Create some initial data in the permanent storage
     */

    @Order(1)
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            List<Roles> rolesList = createRoleJunctionTable(roleRepository);

            if (roleRepository.findByAuthority(Role.ROLE_ADMIN).isEmpty()) {
                createUser("admin", userRepository, passwordEncoder, rolesList.getFirst());
            }

            // create some users
            if (roleRepository.findByAuthority(Role.ROLE_USER).isEmpty()) {
                String[] users = {"name1", "name2", "name3", "name4", "name5"};
                for (String name : users) {
                    createUser(name, userRepository, passwordEncoder, rolesList.getLast());
                }
            }

            if (productRepository.findAll().isEmpty()) {
                addProducts(productRepository);
            }
        };
    }

    private List<Roles> createRoleJunctionTable(RoleRepository roleRepository) {
        List<Roles> rolesList = new ArrayList<>();

        rolesList.add(roleRepository.findByAuthority(Role.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Roles(Role.ROLE_ADMIN))));

        rolesList.add(roleRepository.findByAuthority(Role.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Roles(Role.ROLE_USER))));
        return rolesList;
    }
/*


// (OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, ObjectMapper mapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

    @Profile("test")
    @Order(2)
    @Bean
    CommandLineRunner commandLineRunner(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, ObjectMapper mapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            List<Order> orders = new ArrayList<>();
            boolean activeBasket;
            for (int i = 0; i < 5; i++) {
                activeBasket = i > 1 && i < 4;
                var newOrder = new Order(users.get(i), activeBasket); // fake a basket to order history
                orders.add(newOrder);

            }

            List<OrderQty> user0Basket = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                orders.getFirst().getOrderQty().add(new OrderQty(i, products.get(i), i, orders.getFirst()));
//            orders.getFirst().getOrderQty().add(orderQty);
            }


            List<OrderQty> user1Basket = new ArrayList<>();
            user1Basket.add(new OrderQty(5, products.get(0), 1, orders.get(1)));
            user1Basket.add(new OrderQty(6, products.get(1), 2, orders.get(1)));

            List<OrderQty> user2Basket = new ArrayList<>();
            user2Basket.add(new OrderQty(7, products.get(4), 1, orders.get(2)));
            user2Basket.add(new OrderQty(8, products.get(5), 2, orders.get(2)));

            List<OrderQty> user3Basket = new ArrayList<>();
            user3Basket.add(new OrderQty(9, products.get(4), 55, orders.get(3)));
            user3Basket.add(new OrderQty(10, products.get(5), 66, orders.get(3)));

            List<OrderQty> user4Basket = new ArrayList<>();
            user4Basket.add(new OrderQty(6, products.get(1), 2000, orders.get(4)));

            for (OrderQty orderQty : user0Basket) {
                orders.getFirst().getOrderQty().add(orderQty);
            }
            for (OrderQty orderQty : user1Basket) {
                orders.get(1).getOrderQty().add(orderQty);
            }

            for (OrderQty orderQty : user2Basket) {
                orders.get(2).getOrderQty().add(orderQty);
            }
            for (OrderQty orderQty : user3Basket) {
                orders.get(3).getOrderQty().add(orderQty);
            }
            for (OrderQty orderQty : user4Basket) {
                orders.get(4).getOrderQty().add(orderQty);
            }

            for (int i = 0; i < 5; i++) {
                orderRepository.save(orders.get(i));
            }

//            save orders
//        orderRepository.save(order1);
//        orderRepository.save(order2);
//        orderRepository.save(order3);
//        orderRepository.save(order4);
//        orderRepository.save(order5);


        orderQtyRepository.save(basket1);
        orderQtyRepository.save(basket2);
        orderQtyRepository.save(basket3);
        orderQtyRepository.save(basket4);

        orderQtyRepository.save(basket5);
        orderQtyRepository.save(basket6);

        orderQtyRepository.save(basket7);
        orderQtyRepository.save(basket8);
        orderQtyRepository.save(basket9);
        orderQtyRepository.save(basket10);
        orderQtyRepository.save(basket11);

        }

    };
    */
}

