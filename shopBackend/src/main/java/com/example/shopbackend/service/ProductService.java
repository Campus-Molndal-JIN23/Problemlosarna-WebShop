package com.example.shopbackend.service;

import com.example.shopbackend.entity.Product;
import com.example.shopbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     *
     * @param product
     * @return
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
     * Checks if a products exist, deletes object that exist.
     * @param product
     * @return true if deleted, false if not found
     */
    public boolean delete(Product product) {

        var productExists = productRepository.findById(product.getId()).orElse(null);

        if (productExists == null) {
            return false;
        } else {
            productRepository.delete(product);
            return true;
        }
    }
}

