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
            var user3 = userRepository.save(new User("name3", "password"));
            var user4 = userRepository.save(new User("name4", "password"));
            var user5 = userRepository.save(new User("name5", "password"));

            var product1 = productRepository.save(new Product("Product 1", "Text about the product 1", 100));
            var product2 = productRepository.save(new Product("Product 2", "Text about the product 2", 200));
            var product3 = productRepository.save(new Product("Product 3", "Text about the product 3", 300));
            var product4 = productRepository.save(new Product("Product 4", "Text about the product 4", 400));

            // to easily track the basketDTO implementation
            var product55 = productRepository.save(new Product("product in order history 1", "Text about..", 55));
            var product66 = productRepository.save(new Product("product in order history 2", "Text about..", 66));

            var order1 = new Order(user1, true); // fake a basket to order history
            var order2 = new Order(user2, true);
            var order3 = new Order(user1, false);
            var order4 = new Order(user1, false);
            var order5= new Order(user5,false);

            var basket1 = new OrderQty(1, product1, 1, order1);
            var basket2 = new OrderQty(2, product2, 2, order1);
            var basket3 = new OrderQty(3, product3, 3, order1);
            var basket4 = new OrderQty(4, product4, 4, order1);

            var basket5 = new OrderQty(5, product1, 1, order2);
            var basket6 = new OrderQty(6, product2, 2, order2);




            var basket7 = new OrderQty(7, product55, 1, order3);
            var basket8 = new OrderQty(8, product66, 2, order3);

            var basket9 = new OrderQty(9, product55, 55, order4);
            var basket10 = new OrderQty(10, product66, 66, order4);

            var basket11 = new OrderQty(6, product2, 2000, order5);




            order1.getOrderQty().add(basket1);
            order1.getOrderQty().add(basket2);
            order1.getOrderQty().add(basket3);
            order1.getOrderQty().add(basket4);

            order2.getOrderQty().add(basket5);
            order2.getOrderQty().add(basket6);

            order3.getOrderQty().add(basket7);
            order3.getOrderQty().add(basket8);

            order4.getOrderQty().add(basket9);
            order4.getOrderQty().add(basket10);
            order5.getOrderQty().add(basket11);

//            save orders
            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
            orderRepository.save(order4);
            orderRepository.save(order5);

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

            // Check if the basket exsist
// out to check construction of table
            Optional<Order> fetchOrder1 = orderRepository.findByUserIdAndActiveBasket(user1.getId(), true);
            System.out.println("Order 1 id: " + fetchOrder1.get().getId());

            List<OrderQty> orderQtyList1 = orderQtyRepository.findOrderQtyByOrderId(fetchOrder1.get().getId());
            for (OrderQty orderQty : orderQtyList1) {
                System.out.println("Order qty id:" + orderQty.getId());
            }


            Optional<Order> fetchOrder2 = orderRepository.findByUserIdAndActiveBasket(user2.getId(), true);
            System.out.println("Order 2 id:" + fetchOrder2.get().getId());

            List<OrderQty> orderQtyList2 = orderQtyRepository.findOrderQtyByOrderId(fetchOrder2.get().getId());
            for (OrderQty orderQty : orderQtyList2) {
                System.out.println("Order qty id:" + orderQty.getId());
            }
        };

    }

}
