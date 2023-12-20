package com.example.shopfrontend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.models.dto.OrderDetailsDTO;
import com.example.shopfrontend.models.dto.ProductDTO;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.apache.hc.core5.http.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {AdminController.class, ProductHttp.class, OrderHttp.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @Test
    void testAdminIndex() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            ProductHttp productHttp = mock(ProductHttp.class);
            when(productHttp.getAllProducts()).thenReturn(new ArrayList<>());
            AdminController adminController = new AdminController(productHttp, new OrderHttp());
            String actualAdminIndexResult = adminController.adminIndex(new ConcurrentModel());
            verify(productHttp).getAllProducts();
            assertEquals("admin_index", actualAdminIndexResult);
        }

    }

    /**
     * Method under test: {@link AdminController#createProductForm(Model)}
     */
    @Test
    void testCreateProductForm() {

        ProductHttp productHttp = new ProductHttp();
        AdminController adminController = new AdminController(productHttp, new OrderHttp());
        assertEquals("create_product", adminController.createProductForm(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link AdminController#saveProduct(ProductDTO)}
     */
    @Test
    void testSaveProduct() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            ProductHttp productHttp = mock(ProductHttp.class);
            when(productHttp.createProduct(Mockito.<ProductDTO>any(), Mockito.<String>any())).thenReturn(1);
            AdminController adminController = new AdminController(productHttp, new OrderHttp());
            String actualSaveProductResult = adminController.saveProduct(new ProductDTO());
            verify(productHttp).createProduct(Mockito.<ProductDTO>any(), Mockito.<String>any());
            assertEquals("redirect:/error", actualSaveProductResult);
            assertEquals(1, adminController.status);
        }
    }

    /**
     * Method under test: {@link AdminController#saveProduct(ProductDTO)}
     */
    @Test
    void testSaveProduct3() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            ProductHttp productHttp = mock(ProductHttp.class);
            when(productHttp.createProduct(Mockito.<ProductDTO>any(), Mockito.<String>any())).thenReturn(200);
            AdminController adminController = new AdminController(productHttp, new OrderHttp());
            String actualSaveProductResult = adminController.saveProduct(new ProductDTO());
            verify(productHttp).createProduct(Mockito.<ProductDTO>any(), Mockito.<String>any());
            assertEquals("redirect:/admin", actualSaveProductResult);
            assertEquals(200, adminController.status);
        }

    }

    /**
     * Method under test: {@link AdminController#getAllOrders(Model)}
     */
    @Test
    void testGetAllOrders() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.getAllOrdersForAll(Mockito.<String>any())).thenReturn(new OrderDetailsDTO());
            AdminController adminController = new AdminController(new ProductHttp(), orderHttp);
            String actualAllOrders = adminController.getAllOrders(new ConcurrentModel());
            verify(orderHttp).getAllOrdersForAll(Mockito.<String>any());
            assertEquals("all_orders", actualAllOrders);
        }

    }

    /**
     * Method under test: {@link AdminController#getAllOrders(Model)}
     */
    @Test
    void testGetAllOrders2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.getAllOrdersForAll(Mockito.<String>any())).thenReturn(null);
            AdminController adminController = new AdminController(new ProductHttp(), orderHttp);
            String actualAllOrders = adminController.getAllOrders(new ConcurrentModel());
            verify(orderHttp).getAllOrdersForAll(Mockito.<String>any());
            assertEquals("redirect:/error", actualAllOrders);
        }

    }

    /**
     * Method under test: {@link AdminController#deleteProductForm(long)}
     */
    @Test
    void testDeleteProductForm() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/delete_product/{id}",
                "Uri Variables", "Uri Variables");
        requestBuilder.principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    /**
     * Method under test: {@link AdminController#updateProduct(long, ProductDTO)}
     */
    @Test
    void testUpdateProduct() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/edit_product/{id}",
                "Uri Variables", "Uri Variables");
        requestBuilder.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AdminController#updateProductForm(long, Model)}
     */
    @Test
    void testUpdateProductForm() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/edit_product/{id}",
                "Uri Variables", "Uri Variables");
        requestBuilder.principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
