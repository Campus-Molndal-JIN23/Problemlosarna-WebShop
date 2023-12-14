package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final OrderQtyRepository orderQtyRepository;

    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderQtyRepository orderQtyRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderQtyRepository = orderQtyRepository;
        this.orderRepository = orderRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAllByDeleted(false);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * @param product updates a product
     * @return the updated product if fail null
     */
    public Product update(Product product) {

        // check that the product exists
        var findProduct = productRepository.findById(product.getId()).orElse(null);

        if (findProduct == null) {
            return null;
        } else {
            return productRepository.save(product);
        }
    }

    /**
     * Checks if a products exist, Set item to deleted = true and removes from all active baskets.
     *
     * @param productId primary key of a product
     * @return true if deleted, false if not found
     */
    @Transactional
    public boolean delete(long productId) {

        var productExists = productRepository.findById(productId).orElse(null);

        if (productExists == null) {
            return false;
        } else {
            productExists.setDeleted(true);
            save(productExists);

            // find all active baskets and delete there
            Optional<List<Order>> activeBasket = orderRepository.getAllByActiveBasket(true);

            if (activeBasket.isPresent()) {
                for (int i = 0; i < activeBasket.get().size(); i++) {
                    orderQtyRepository.deleteOrderQtyByOrder_IdAndProductId(activeBasket.get().get(i).getId(), productId);
                }
            }
            return true;
        }
    }
}
