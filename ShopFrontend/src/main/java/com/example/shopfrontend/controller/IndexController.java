package com.example.shopfrontend.controller;

import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.*;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;

@Controller
@AllArgsConstructor

public class IndexController {

    private ProductHttp productHttp;
    private  UserHttp userHttp;
    static LoginResponse loginResponse = new LoginResponse();

    @GetMapping("/index")
    public String listProducts(Model model) throws IOException, ParseException {
        model.addAttribute("products", productHttp.getAllProducts());
        return "index";
    }

    @GetMapping("/index/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model) throws IOException, ParseException {
        ProductRespons product = productHttp.getProductById(id);
        model.addAttribute("product", product);
        return "view_one_product";
    }

    @GetMapping("/login")
    public String login(Model model) {
        LoginForm loginform = new LoginForm();
        model.addAttribute("loginform", loginform);
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(LoginForm user) throws IOException, ParseException {
        loginResponse = userHttp.loginUser(user);

        if (loginResponse == null) { //if the is no user
            return "redirect:/registration";
        }
        else {
            if (loginResponse.getRole().equals("ADMIN")) {
                return "redirect:/admin_index";
            } else {
                return "redirect:/user_index";
            }
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(RegistrationForm form) throws IOException, ParseException {
        userHttp.registerUser(form);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        loginResponse = null;
        return "redirect:/index";
    }
}
