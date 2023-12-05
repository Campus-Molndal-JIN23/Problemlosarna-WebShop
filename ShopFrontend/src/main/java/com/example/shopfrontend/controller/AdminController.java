package com.example.shopfrontend.controller;

import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.Product;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

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
        return "admin_index";
    }

    @GetMapping ("/admin/create_product")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "create_product";
    }

    @PostMapping("/admin/create_product")
    public String saveProduct(Product product) {
        productHttp.createProduct(product);
        return "redirect:/admin_index";
    }

    @GetMapping("/admin/edit_product")
    public String updateProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "update_product";
    }

    @PostMapping("/admin/edit_product")
    public String updateProduct(Product product) {
        productHttp.updateProduct(product);
        return "redirect:/admin_index";
    }

    @GetMapping("/admin/delete_product")
    public String deleteProductForm(@PathVariable long id) {
        productHttp.deleteProductById(id);
        return "redirect:/admin_index";
    }

    @GetMapping("/admin/all_orders")
    public String getAllOrders(Model model) throws IOException, ParseException {
        model.addAttribute("orders", orderHttp.getAllOrders());
        return "all_orders";
    }


}
