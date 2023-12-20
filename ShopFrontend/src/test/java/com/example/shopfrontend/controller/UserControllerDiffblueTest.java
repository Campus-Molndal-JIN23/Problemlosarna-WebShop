package com.example.shopfrontend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.shopfrontend.http.BasketHttp;
import com.example.shopfrontend.http.OrderHttp;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;
import com.example.shopfrontend.models.dto.BasketDTO;
import com.example.shopfrontend.models.dto.OrderDTO;
import com.example.shopfrontend.models.dto.UpdateBasketDTO;

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

@ContextConfiguration(classes = {UserController.class, ProductHttp.class, OrderHttp.class, BasketHttp.class,
        UserHttp.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    /**
     * Method under test: {@link UserController#userIndex(Model)}
     */
    @Test
    void testUserIndex() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            ProductHttp productHttp = mock(ProductHttp.class);
            when(productHttp.getAllProducts()).thenReturn(new ArrayList<>());
            OrderHttp orderHttp = new OrderHttp();
            BasketHttp basketHttp = new BasketHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualUserIndexResult = userController.userIndex(new ConcurrentModel());
            verify(productHttp).getAllProducts();
            assertEquals("user_index", actualUserIndexResult);
        }
    }

    /**
     * Method under test: {@link UserController#getBasket(Model)}
     */
    @Test
    void testGetBasket() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            BasketHttp basketHttp = mock(BasketHttp.class);
            when(basketHttp.getBasket(Mockito.<String>any())).thenReturn(new BasketDTO());
            ProductHttp productHttp = new ProductHttp();
            OrderHttp orderHttp = new OrderHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualBasket = userController.getBasket(new ConcurrentModel());
            verify(basketHttp).getBasket(Mockito.<String>any());
            assertEquals("user_basket", actualBasket);
        }

    }

    /**
     * Method under test: {@link UserController#getBasket(Model)}
     */
    @Test
    void testGetBasket2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            BasketHttp basketHttp = mock(BasketHttp.class);
            when(basketHttp.getBasket(Mockito.<String>any())).thenReturn(null);
            ProductHttp productHttp = new ProductHttp();
            OrderHttp orderHttp = new OrderHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualBasket = userController.getBasket(new ConcurrentModel());
            verify(basketHttp).getBasket(Mockito.<String>any());
            assertEquals("redirect:/error", actualBasket);
        }

    }



    /**
     * Method under test: {@link UserController#viewUserDetails(Model)}
     */
    @Test
    void testViewUserDetails2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.getUserDetails(Mockito.<String>any())).thenReturn(null);
            ProductHttp productHttp = new ProductHttp();
            OrderHttp orderHttp = new OrderHttp();
            UserController userController = new UserController(productHttp, orderHttp, new BasketHttp(), userHttp);
            String actualViewUserDetailsResult = userController.viewUserDetails(new ConcurrentModel());
            verify(userHttp).getUserDetails(Mockito.<String>any());
            assertEquals("redirect:/error", actualViewUserDetailsResult);
        }

    }

    /**
     * Method under test: {@link UserController#getOrders(Model)}
     */
    @Test
    void testGetOrders() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.getAllOrdersForOne(Mockito.<String>any())).thenReturn(new OrderDTO());
            ProductHttp productHttp = new ProductHttp();
            BasketHttp basketHttp = new BasketHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualOrders = userController.getOrders(new ConcurrentModel());
            verify(orderHttp).getAllOrdersForOne(Mockito.<String>any());
            assertEquals("user_past_orders", actualOrders);
        }

    }

    /**
     * Method under test: {@link UserController#getOrders(Model)}
     */
    @Test
    void testGetOrders2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.getAllOrdersForOne(Mockito.<String>any())).thenReturn(null);
            ProductHttp productHttp = new ProductHttp();
            BasketHttp basketHttp = new BasketHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualOrders = userController.getOrders(new ConcurrentModel());
            verify(orderHttp).getAllOrdersForOne(Mockito.<String>any());
            assertEquals("redirect:/error", actualOrders);
        }
    }

    /**
     * Method under test: {@link UserController#addToBasket(long)}
     */
    @Test
    void testAddToBasket() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/basket/add/{id}", "Uri Variables",
                "Uri Variables");
        requestBuilder.principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link UserController#checkoutBasket()}
     */
    @Test
    void testCheckoutBasket() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.placeOrder(Mockito.<String>any())).thenReturn(1);
            ProductHttp productHttp = new ProductHttp();
            BasketHttp basketHttp = new BasketHttp();
            String actualCheckoutBasketResult = (new UserController(productHttp, orderHttp, basketHttp, new UserHttp()))
                    .checkoutBasket();
            verify(orderHttp).placeOrder(Mockito.<String>any());
            assertEquals("redirect:/error", actualCheckoutBasketResult);
        }
    }

    /**
     * Method under test: {@link UserController#checkoutBasket()}
     */
    @Test
    void testCheckoutBasket2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            OrderHttp orderHttp = mock(OrderHttp.class);
            when(orderHttp.placeOrder(Mockito.<String>any())).thenReturn(204);
            ProductHttp productHttp = new ProductHttp();
            BasketHttp basketHttp = new BasketHttp();
            UserController userController = new UserController(productHttp, orderHttp, basketHttp, new UserHttp());
            String actualCheckoutBasketResult = userController.checkoutBasket();
            verify(orderHttp).placeOrder(Mockito.<String>any());
            assertEquals("Order placed successfully", userController.message);
            assertEquals("redirect:/user", actualCheckoutBasketResult);
        }
    }

    /**
     * Method under test: {@link UserController#removeBasketItem(long)}
     */
    @Test
    void testRemoveBasketItem() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/basket/remove/{id}",
                "Uri Variables", "Uri Variables");
        requestBuilder.principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link UserController#updateBasketItem(long, UpdateBasketDTO)}
     */
    @Test
    void testUpdateBasketItem() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/basket/edit/{id}",
                "Uri Variables", "Uri Variables");
        requestBuilder.principal(new UserDatabaseRealm.UserDatabasePrincipal(user, new MemoryUserDatabase()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
