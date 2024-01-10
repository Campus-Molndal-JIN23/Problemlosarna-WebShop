package com.example.shopbackend;

import com.example.shopbackend.entity.*;
import com.example.shopbackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
        productRepository.save(new Product("Gaspiston", "A regular piston driven by gas. Suitable for a single-engine airplane.", 105));
        productRepository.save(new Product("Shovel Wheel, Small", "A small shovel wheel.", 555));
        productRepository.save(new Product("Shovel Wheel, Large", "A large shovel wheel", 575));
        productRepository.save(new Product("Pikachu", "Regular Pikachu. An in-real-life one.", 2500));
        productRepository.save(new Product("Argumentation", "30 minutes of plain old good argumentation.", 300));
        productRepository.save(new Product("M5 Nut, 1 piece", "1st M5 Nut.", 5));
        productRepository.save(new Product("Boat Chassis, Stena Type", "In steel construction. Wood will come in a later version.", 350000000));
        productRepository.save(new Product("Brown Duck Feather", "Duck feather. Brown.", 15));
        productRepository.save(new Product("Subscription Kalle & Hobbe", "Monthly subscription to the comic series Kalle & Hobbe.", 299));
        productRepository.save(new Product("M6 Screw, 2 pieces", "2 M6 screws.", 16));
        productRepository.save(new Product("Isopropanol, 10 Liters", "10 liters of Isopropanol.", 50));
        productRepository.save(new Product("Artificial Snow, 5 Liters", "Bag of ready-made artificial snow. Recommended to be transported to cold storage as soon as possible.", 500));
        productRepository.save(new Product("Breakfast Cereals, Used", "Breakfast cereals, previously demo-ex in a bowl.", 25));
        productRepository.save(new Product("Pen, Small", "A small pen.", 10));
        productRepository.save(new Product("Swedish Christmas Soft Drink", "A Swedish Christmas soft drink called \"Julmust\".", 575));
        productRepository.save(new Product("Authentic Musketeer, 1700s", "An authentic musket from the 18th century.", 4000000));
        productRepository.save(new Product("Evolved Pikachu", "An evolved Pikachu. I think he's called Ra.. Rai.. Raicha something?", 4500));
        productRepository.save(new Product("Windows 95 Recovery Disk", "A recovery disk for Windows 95. This is a really good price.", 999));
        productRepository.save(new Product("M4 Screw, 5 pieces", "5 M4 screws.", 5));
        productRepository.save(new Product("Screwdriver, Buffalo & Bow Brand", "A screwdriver. Brand Buffalo & Bow.", 499));
        productRepository.save(new Product("Fork, 3 pieces", "A pack of 3 forks.", 25));
        productRepository.save(new Product("Fastening Bolt, Large Shovel Wheel", "A fastening bolt for the large shovel wheel.", 1500));
        productRepository.save(new Product("A Nice Neighbor", "A neighbor of the nice model. Extremely rare in some cases.", 450));
        productRepository.save(new Product("Terminal Mayhem Gold Edition v.3.87 hotfix", "A hotfix for the spectacular gold edition of the game Terminal Mayhem v3.87. Only 3 ones left so hurry up before it goes away!", 451));
        productRepository.save(new Product("Terminal Mayhem Ring of the Unlucky", "The most item of items you can get in the gaming world.", 999999999));
    }

    private static void makeOrders(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, List<User> users, List<Product> products, Boolean isActive) {
        for (int i = 1; i < users.size(); i++) {
            var order = new com.example.shopbackend.entity.Order(users.get(i), isActive);

            for (int j = 0; j < products.size(); j++) {
                if (j == 0) continue;
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

    @Order(2)
    @Bean
    CommandLineRunner createTestDatabase(OrderQtyRepository orderQtyRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        return args -> {
            // Get a list of users
            List<User> users = userRepository.findAll();
            // Get a list of products
            List<Product> products = productRepository.findAll();

            if (orderRepository.findAll().isEmpty()) {
                // make some active baskets
                makeOrders(orderQtyRepository, orderRepository, users, products, true);

                // make some order history
                for (int i = 0; i < 2; i++) {
                    makeOrders(orderQtyRepository, orderRepository, users, products, false);
                }
            }
        };
    }
}

