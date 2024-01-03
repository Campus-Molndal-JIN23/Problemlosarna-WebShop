package com.example.shopfrontend.controller;

import com.example.shopfrontend.form.LoginForm;
import com.example.shopfrontend.form.LoginResponse;
import com.example.shopfrontend.form.RegistrationForm;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for handling requests from the index page.
 * It uses a Logger to log the requests and responses to the console.
 */

@Controller
@Slf4j
public class IndexController {

    private final ProductHttp productHttp;
    private final UserHttp userHttp;

    // this variable is used to store the current user
    public static LoginResponse currentUser = new LoginResponse();

    // the following variables are used to redirect the user to the appropriate page
    private final String ERROR_URL = "/error";
    private final String ADMIN_URL = "/admin";
    private final String USER_URL = "/user";
    private final String LOGIN_URL = "/login";
    private final String REGISTRATION_URL = "/registration";
    private static final String INDEX_URL = "/index";
    private final String ADMIN_ROLE = "ROLE_ADMIN";
    private final String USER_ROLE = "ROLE_USER";
    String loginErrorMessage = "";
    String registrationErrorMessage = "";
    int status = 0;

    public IndexController(ProductHttp productHttp, UserHttp userHttp) {
        this.productHttp = productHttp;
        this.userHttp = userHttp;
    }

    // This method is responsible for rendering the index page and is called when the user navigates to the root url.
    // its manin purpose is to redirect the user to the index page, if not written the correct URL.
    // It lists all products and displays them on the index page.
    @GetMapping("")
    public String listProducts(Model model) throws IOException, ParseException {
        List<ProductDTO> products = productHttp.getAllProducts();
        if(products == null) {
            log.info("products not found");
            return "redirect:" + ERROR_URL;
        } else {
            model.addAttribute("products", products);
            return "index";
        }
    }

    // This method is responsible for rendering the index page and is called when the user navigates to the /index url.
    // It lists all products and displays them on the index page.
    // if the product list is null or not fetched correctly, the user is redirected to the error page.
    @GetMapping("/index")
    public String listProductsIndex(Model model) throws IOException, ParseException {
        List<ProductDTO> products = productHttp.getAllProducts();
        if(products == null) {
            log.info("products not found");
            return "redirect:" + ERROR_URL;
        } else {
            model.addAttribute("products", products);
            return "index";
        }
    }

    @GetMapping("/index/one/{id}")
    public String getOneProduct(@PathVariable long id, Model model1) throws IOException, ParseException {
        ProductDTO product = productHttp.getProductById(id);
        if(product == null) {
            log.info("product not found");
            return "redirect:" + ERROR_URL;
        } else {
            model1.addAttribute("product", product);
            return "view_one_product";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        LoginForm loginform = new LoginForm();
        model.addAttribute("loginform", loginform);
        model.addAttribute("errorMessage", loginErrorMessage);
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(LoginForm user) throws IOException, ParseException {
        status = userHttp.loginUser(user);
        if (status == 200) {
            if (currentUser.getRole().contains(ADMIN_ROLE)) {
                return "redirect:" + ADMIN_URL;
            } else {
                return "redirect:" + USER_URL;
            }
        }
        if (status == 401) {
            loginErrorMessage = "Wrong username or password";
            return "redirect:" + LOGIN_URL;
        }
        if (status == 404) {
            registrationErrorMessage = "User not found, please register";
            return "redirect:" + REGISTRATION_URL;
        } else {
            loginErrorMessage = "Something went wrong with the login";
            return "redirect:" + LOGIN_URL;
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
        model.addAttribute("errorMessage", registrationErrorMessage);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(RegistrationForm form) throws IOException {
        status = userHttp.registerUser(form);
        if (status == 200) {
            return "redirect:" + LOGIN_URL;
        }
        if (status == 409) {
            loginErrorMessage = "Username already exists, please Login";
            return "redirect:" + LOGIN_URL;
        } else {
            registrationErrorMessage = "Something went wrong with the registration";
            return "redirect:" + REGISTRATION_URL;
        }
    }

    @GetMapping("/logout")
    public static String logout() {
        currentUser = new LoginResponse();
        return "redirect:" + INDEX_URL;
    }

    @GetMapping("/error")
    public String error(Model model) {

        if (currentUser.getRole().contains(ADMIN_ROLE)) {
            model.addAttribute("home", ADMIN_URL);
        }
        if (currentUser.getRole().contains(USER_ROLE)) {
            model.addAttribute("home", USER_URL);
        } else {
            model.addAttribute("home", INDEX_URL);
        }
        log.info("error getting role");
        return "error";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model) {
        if (currentUser.getRole().contains(ADMIN_ROLE)) {
            model.addAttribute("home", ADMIN_URL);
        }
        if (currentUser.getRole().contains(USER_ROLE)) {
            model.addAttribute("home", USER_URL);
        } else {
            model.addAttribute("home", INDEX_URL);
        }
        log.info("error getting role");
        return "unauthorized";
    }
}
