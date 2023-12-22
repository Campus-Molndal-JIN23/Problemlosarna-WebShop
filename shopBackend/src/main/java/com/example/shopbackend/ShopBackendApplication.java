package com.example.shopbackend;

import com.example.shopbackend.entity.*;
import com.example.shopbackend.repository.*;
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
        productRepository.save(new Product("Product 2", "Text about the product 2", 200));
        productRepository.save(new Product("Product 3", "Text about the product 3", 300));
        productRepository.save(new Product("Product 4", "Text about the product 4", 400));
        productRepository.save(new Product("product in order history 1", "Text about..", 55));
        productRepository.save(new Product("product in order history 2", "Text about..", 66));
        productRepository.save(new Product("One to delete in test", "Text about..", 365));
    }

    private static void makeOrders(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, List<User> users, List<Product> products, Boolean isActive) {
        for (int i = 1; i < users.size(); i++) {
            var order = new com.example.shopbackend.entity.Order(users.get(i), isActive);

            for (int j = 0; j < products.size(); j++) {
                var basketProduct = new OrderQty(products.get(j), j, order);
                order.getOrderQty().add(basketProduct);
                orderRepository.save(order);
                orderQtyRepository.save(basketProduct);
            }
        }
    }

    /**
     * Create some initial data in the permanent storage
     */

    @Order(1)
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            List<Roles> rolesList = createRoleTable(roleRepository);

            // make admin if not found
            if (userRepository.findUserByUserName("admin").isEmpty()) {
                createUser("admin", userRepository, passwordEncoder, rolesList.getFirst());
            }

            String[] users = {"name1", "name2", "name3", "name4", "name5"};
            for (String name : users) {
                if (userRepository.findUserByUserName(name).isEmpty()) {
                    createUser(name, userRepository, passwordEncoder, rolesList.getLast());
                }
            }
            if (productRepository.findAll().isEmpty()) {
                addProducts(productRepository);
            }
        };
    }

    private List<Roles> createRoleTable(RoleRepository roleRepository) {
        List<Roles> rolesList = new ArrayList<>();

        if (roleRepository.findByAuthority(Role.ROLE_ADMIN).isEmpty()) {
            saveRoles(roleRepository, Role.ROLE_ADMIN);
            saveRoles(roleRepository, Role.ROLE_USER);
        }
        rolesList.add(roleRepository.findByAuthority(Role.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Roles(Role.ROLE_ADMIN))));

        rolesList.add(roleRepository.findByAuthority(Role.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Roles(Role.ROLE_USER))));
        return rolesList;
    }

    public void saveRoles(RoleRepository repository, Role role) {
        Roles roles = new Roles();
        roles.setAuthority(role);
        repository.save(roles);
    }

    //    @Profile("test")
    @Order(2)
    @Bean
    CommandLineRunner createTestDatabase(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        return args -> {

            // Get a list of users
            List<User> users = userRepository.findAll();
            // Get a list of products
            List<Product> products = productRepository.findAll();

            // make some active baskets
            makeOrders(orderQtyRepository, orderRepository, users, products, true);
            // make some order history
            for (int i = 0; i < 2; i++) {
                makeOrders(orderQtyRepository, orderRepository, users, products, false);
            }
        };
    }
}

