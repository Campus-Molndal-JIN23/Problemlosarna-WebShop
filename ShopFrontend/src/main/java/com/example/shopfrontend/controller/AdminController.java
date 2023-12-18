package com.example.shopfrontend.controller;

import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.OrderDetailsDTO;
import com.example.shopfrontend.models.Product;

import com.example.shopfrontend.models.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    private final ProductHttp productHttp;
    private final OrderHttp orderHttp;

    int status = 0;


    public AdminController(ProductHttp productHttp, OrderHttp orderHttp) {
        this.productHttp = productHttp;
        this.orderHttp = orderHttp;
    }

    @GetMapping("/admin")
    public String adminIndex(Model model) throws IOException, ParseException {
        List<ProductDTO> products = productHttp.getAllProducts();
        if(products == null) {
            return "redirect:/error";
        } else {
            model.addAttribute("products", products);
            model.addAttribute("username", IndexController.currentUser.getUsername());
            return "admin_index";
        }
    }


    @GetMapping ("/admin/create_product")
    public String createProductForm(Model model) {
        ProductDTO product = new ProductDTO();
        model.addAttribute("product", product);
        return "create_product";
    }

    @PostMapping("/admin/create_product")
    public String saveProduct(ProductDTO product) throws IOException, ParseException {
        status = productHttp.createProduct(product,IndexController.currentUser.getToken());
        if (status == 200) {
            return "redirect:/admin";
        }
        if (status == 401 || status == 403) {
            return "redirect:/unauthorized";
        }
        else {
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/edit_product/{id}")
    public String updateProductForm(@PathVariable long id ,Model model) throws IOException, ParseException {
        ProductDTO product = productHttp.getProductById(id);
        if(product == null) {
            return "redirect:/unauthorized";
        } else {
            model.addAttribute("product", product);
            return "update_product";
        }
    }

    @PostMapping ("/admin/edit_product/{id}")
    public String updateProduct(@PathVariable long id , @ModelAttribute ProductDTO product) throws IOException, ParseException {
        ProductDTO productToUpdate = productHttp.getProductById(id);
        if (productToUpdate == null) {
            return "redirect:/error";
        }
        productToUpdate.setId(product.getId());
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setDescription(product.getDescription());
        status = productHttp.updateProduct(productToUpdate,IndexController.currentUser.getToken());
        if (status == 200) {
            log.info("updateProduct: " + productToUpdate);
            return "redirect:/admin";
        } else {
            return "redirect:/unauthorized";
        }
    }

    @GetMapping("/admin/delete_product/{id}")
    public String deleteProductForm(@PathVariable long id) throws IOException {
        ProductDTO productToDelete = new ProductDTO();
        productToDelete.setId(id);
        status = productHttp.deleteProductById(productToDelete,IndexController.currentUser.getToken());
        if (status == 200) {
            return "redirect:/admin";
        }
        if (status == 401 || status == 403) {
            return "redirect:/unauthorized";
        }
        else {
            return "redirect:/error";
        }
    }

    @GetMapping("/admin/all_orders")
    public String getAllOrders(Model model) throws IOException, ParseException {
        OrderDetailsDTO orders = orderHttp.getAllOrdersForAll(IndexController.currentUser.getToken());
        if(orders == null) {
            return "redirect:/unauthorized";
        } else {
            model.addAttribute("pastOrders", orders);
            model.addAttribute("username", IndexController.currentUser.getUsername());
            return "all_orders";
        }
    }
}
