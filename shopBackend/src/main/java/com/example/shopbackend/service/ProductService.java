package com.example.shopbackend.service;

import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.model.ProductDTO;
import com.example.shopbackend.repository.OrderQtyRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAllByDeleted(false);
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products)
            dtoList.add(new ProductDTO(product));

        return dtoList;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductDTO save(ProductDTO product) {
        // catch String name unique = true errors
        try {
            // Returns ProductDTO saves a Product constructed from ProductDTO
            return new ProductDTO(productRepository.save(new Product(product)));

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * @param product updates a product
     * @return the updated product if fail null
     */
    public Product update(ProductDTO product) {

        // check that the product exists
        Product updateProduct = productRepository.findById(product.id()).orElse(null);

        if (updateProduct == null) {
            return null;
        } else {
            try {
                updateProduct.setPrice(product.price());
                updateProduct.setName(product.name());
                updateProduct.setDescription(product.description());
                return productRepository.save(updateProduct);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                return null;
            }
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

        Product productExists = productRepository.findById(productId).orElse(null);

        if (productExists == null || productExists.isDeleted()) {
            return false;
        } else {
            productExists.setDeleted(true);
            productRepository.save(productExists);

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
