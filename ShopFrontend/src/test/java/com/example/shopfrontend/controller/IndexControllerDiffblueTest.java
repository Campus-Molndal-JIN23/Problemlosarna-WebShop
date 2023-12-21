package com.example.shopfrontend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.shopfrontend.form.LoginForm;
import com.example.shopfrontend.form.RegistrationForm;
import com.example.shopfrontend.http.ProductHttp;
import com.example.shopfrontend.http.UserHttp;

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

@ContextConfiguration(classes = {IndexController.class, ProductHttp.class, UserHttp.class})
@ExtendWith(SpringExtension.class)
class IndexControllerTest {
    @Autowired
    private IndexController indexController;

    /**
     * Method under test: {@link IndexController#listProducts(Model)}
     */
    @Test
    void testListProducts() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            ProductHttp productHttp = mock(ProductHttp.class);
            when(productHttp.getAllProducts()).thenReturn(new ArrayList<>());
            IndexController indexController = new IndexController(productHttp, new UserHttp());
            String actualListProductsResult = indexController.listProducts(new ConcurrentModel());
            verify(productHttp).getAllProducts();
            assertEquals("index", actualListProductsResult);
        }
    }

    /**
     * Method under test: {@link IndexController#login(Model)}
     */
    @Test
    void testLogin() {
        ProductHttp productHttp = new ProductHttp();
        IndexController indexController = new IndexController(productHttp, new UserHttp());
        assertEquals("login", indexController.login(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link IndexController#login(Model)}
     */
    @Test
    void testLogin2() {
        ProductHttp productHttp = mock(ProductHttp.class);
        IndexController indexController = new IndexController(productHttp, new UserHttp());
        assertEquals("login", indexController.login(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link IndexController#loginUser(LoginForm)}
     */
    @Test
    void testLoginUser() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.loginUser(Mockito.<LoginForm>any())).thenReturn(401);
            IndexController indexController = new IndexController(new ProductHttp(), userHttp);
            LoginForm user = new LoginForm();
            user.setPassword("iloveyou");
            user.setUserName("janedoe");
            String actualLoginUserResult = indexController.loginUser(user);
            verify(userHttp).loginUser(Mockito.<LoginForm>any());
            assertEquals("Wrong username or password", indexController.loginErrorMessage);
            assertEquals("redirect:/registration", actualLoginUserResult);
            assertEquals(401, indexController.status);
        }
    }

    /**
     * Method under test: {@link IndexController#loginUser(LoginForm)}
     */
    @Test
    void testLoginUser2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.loginUser(Mockito.<LoginForm>any())).thenReturn(404);
            IndexController indexController = new IndexController(new ProductHttp(), userHttp);
            LoginForm user = new LoginForm();
            user.setPassword("iloveyou");
            user.setUserName("janedoe");
            String actualLoginUserResult = indexController.loginUser(user);
            verify(userHttp).loginUser(Mockito.<LoginForm>any());
            assertEquals("User not found", indexController.loginErrorMessage);
            assertEquals("redirect:/registration", actualLoginUserResult);
            assertEquals(404, indexController.status);
        }
    }

    /**
     * Method under test: {@link IndexController#registration(Model)}
     */
    @Test
    void testRegistration() {
        ProductHttp productHttp = new ProductHttp();
        IndexController indexController = new IndexController(productHttp, new UserHttp());
        assertEquals("registration", indexController.registration(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link IndexController#registration(Model)}
     */
    @Test
    void testRegistration2() {
        ProductHttp productHttp = mock(ProductHttp.class);
        IndexController indexController = new IndexController(productHttp, new UserHttp());
        assertEquals("registration", indexController.registration(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link IndexController#registerUser(RegistrationForm)}
     */
    @Test
    void testRegisterUser() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.registerUser(Mockito.<RegistrationForm>any())).thenReturn(1);
            IndexController indexController = new IndexController(new ProductHttp(), userHttp);
            RegistrationForm form = new RegistrationForm();
            form.setPassword("iloveyou");
            form.setUserName("janedoe");
            String actualRegisterUserResult = indexController.registerUser(form);
            verify(userHttp).registerUser(Mockito.<RegistrationForm>any());
            assertEquals("Something went wrong with the registration", indexController.loginErrorMessage);
            assertEquals("redirect:/registration", actualRegisterUserResult);
            assertEquals(1, indexController.status);
        }
    }

    /**
     * Method under test: {@link IndexController#registerUser(RegistrationForm)}
     */
    @Test
    void testRegisterUser2() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.registerUser(Mockito.<RegistrationForm>any())).thenReturn(200);
            IndexController indexController = new IndexController(new ProductHttp(), userHttp);
            RegistrationForm form = new RegistrationForm();
            form.setPassword("iloveyou");
            form.setUserName("janedoe");
            String actualRegisterUserResult = indexController.registerUser(form);
            verify(userHttp).registerUser(Mockito.<RegistrationForm>any());
            assertEquals("redirect:/login", actualRegisterUserResult);
            assertEquals(200, indexController.status);
        }
    }

    /**
     * Method under test: {@link IndexController#registerUser(RegistrationForm)}
     */
    @Test
    void testRegisterUser3() throws IOException, ParseException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{null});
            UserHttp userHttp = mock(UserHttp.class);
            when(userHttp.registerUser(Mockito.<RegistrationForm>any())).thenReturn(409);
            IndexController indexController = new IndexController(new ProductHttp(), userHttp);
            RegistrationForm form = new RegistrationForm();
            form.setPassword("iloveyou");
            form.setUserName("janedoe");
            String actualRegisterUserResult = indexController.registerUser(form);
            verify(userHttp).registerUser(Mockito.<RegistrationForm>any());
            assertEquals("Username already exists", indexController.loginErrorMessage);
            assertEquals("redirect:/registration", actualRegisterUserResult);
            assertEquals(409, indexController.status);
        }
    }

    /**
     * Method under test: {@link IndexController#error()}
     */
    @Test
    void testError() {
        ProductHttp productHttp = new ProductHttp();
        assertEquals("error", (new IndexController(productHttp, new UserHttp())).error());
    }

    /**
     * Method under test: {@link IndexController#error()}
     */
    @Test
    void testError2() {
        ProductHttp productHttp = mock(ProductHttp.class);
        assertEquals("error", (new IndexController(productHttp, new UserHttp())).error());
    }

    /**
     * Method under test: {@link IndexController#getOneProduct(long, Model)}
     */
    @Test
    void testGetOneProduct() throws Exception {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(user,
                new MemoryUserDatabase());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/index/one/{id}", "Uri Variables",
                "Uri Variables");
        requestBuilder.principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(indexController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link IndexController#logout()}
     */
    @Test
    void testLogout() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/logout");
        MockMvcBuilders.standaloneSetup(indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/index"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/index"));
    }

    /**
     * Method under test: {@link IndexController#logout()}
     */
    @Test
    void testLogout2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/logout");
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/index"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/index"));
    }
}
