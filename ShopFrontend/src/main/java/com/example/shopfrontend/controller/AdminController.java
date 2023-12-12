package com.example.shopfrontend.controller;

import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.Product;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
public class AdminController {

    private final ProductHttp productHttp;
    private final OrderHttp orderHttp;

    public AdminController(ProductHttp productHttp, OrderHttp orderHttp) {
        this.productHttp = productHttp;
        this.orderHttp = orderHttp;
    }

    @GetMapping("/admin")
    public String adminIndex(Model model) throws IOException, ParseException {
        model.addAttribute("products", productHttp.getAllProducts());
        model.addAttribute("username", IndexController.currentUser.getUsername());
        return "admin_index";
    }

    @GetMapping("/admin/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        model1.addAttribute("product", productHttp.getProductById(id));
        return "admin_view_one_product";
    }

    @GetMapping ("/admin/create_product")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "create_product";
    }

    @PostMapping("/admin/create_product")
    public String saveProduct(Product product) throws IOException, ParseException {
        productHttp.createProduct(product,IndexController.currentUser.getToken());
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit_product/{id}")
    public String updateProductForm(@PathVariable long id ,Model model) throws IOException, ParseException {
        model.addAttribute("product", productHttp.getProductById(id));
        return "update_product";
    }

    @PostMapping ("/admin/edit_product/{id}")
    public String updateProduct(@PathVariable long id , @ModelAttribute Product product) throws IOException, ParseException {
        Product productToUpdate = productHttp.getProductById(id);
        productToUpdate.setId(product.getId());
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setDescription(product.getDescription());
        productHttp.updateProduct(productToUpdate,IndexController.currentUser.getToken());
        log.info("updateProduct: " + productToUpdate);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete_product/{id}")
    public String deleteProductForm(@PathVariable long id) throws IOException {
        productHttp.deleteProductById(id,IndexController.currentUser.getToken());
        return "redirect:/admin";
    }


    @GetMapping("/admin/all_orders")
    public String getAllOrders(Model model) throws IOException, ParseException {
        model.addAttribute("orders", orderHttp.getAllOrders(IndexController.currentUser.getToken()));
        model.addAttribute("username", IndexController.currentUser.getUsername());
        return "all_orders";
    }




}
