package com.example.shopbackend.security.configuration;

import com.example.shopbackend.form.LoginForm;
import com.example.shopbackend.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestRequestMatchers {

    private final String roleAdmin = "ADMIN";
    private final String roleUser = "USER";
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void SfcAllAccessToAuthorizationRegister() throws Exception {
        String url = "/webshop/auth/register";
        var payload = new LoginForm("user", "Password1");
        this.mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    void SfcAllAccessToAuthorizationLogin() throws Exception {
        String url = "/webshop/auth/login";
        var payload = new LoginForm("user", "Password1");
        this.mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }

    @Test
    void SfcAllAccessToProducts() throws Exception {
        String url = "/webshop/products";

        this.mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = roleUser)
    @Test
    void SfcRequiresAuthorizationToAccessSpecificProductWithAuthUserIsOk() throws Exception {
        String url = "/webshop/products";
        this.mvc.perform(get(url + "/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = roleAdmin)
    @Test
    void SfcRequiresAuthorizationToAccessSpecificProductWithAuthAdminIsOk() throws Exception {
        String url = "/webshop/products";
        this.mvc.perform(get(url + "/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = roleUser)
    @Test
    void SfcRequiresAuthorizationToPostProductWithAuthUserIsForbidden() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(0L, "The name", "the description", 42);
        this.mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = roleAdmin)
    @Test
    void SfcRequiresAuthorizationToPostProductWithAuthAdminIsOk() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(0L, "The name", "the description", 42);
        this.mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk());
    }

    @Test
    void SfcRequiresAuthorizationToPutProductWithNoAuthIsForbidden() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(0L, "The name", "the description", 42);
        this.mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = roleUser)
    @Test
    void SfcRequiresAuthorizationToPutProductWithAuthUserIsForbidden() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(1L, "The name", "the description", 42);
        this.mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = roleAdmin)
    @Test
    void SfcRequiresAuthorizationToPutProductWithAuthAdminIsOk() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(1L, "The name", "the description", 42);
        this.mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk());
    }


    @Test
    void SfcRequiresAuthorizationToDeleteProductWithNoAuthIsForbidden() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(5L, "The name", "the description", 42);
        this.mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = roleUser)
    @Test
    void SfcRequiresAuthorizationToDeleteProductWithAuthUserIsForbidden() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(5L, "The name", "the description", 42);
        this.mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = roleAdmin)
    @Test
    void SfcRequiresAuthorizationToDeleteProductWithAuthAdminIsOk() throws Exception {
        String url = "/webshop/products";
        var payload = new ProductDTO(5L, "The name", "the description", 42);
        this.mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk());
    }
}
