package com.example.shopbackend.security.configuration;

import com.example.shopbackend.security.jwt.JwtAuthenticationFilter;
import com.example.shopbackend.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final static String USER_WHITE_LIST = "webshop/user**";

    private final static String[] GET_USER_WHITE_LIST = {"/webshop/basket*", "/webshop/products", ""};
    private final static String[] GET_ADMIN_WHITE_LIST = {"", ""};

    private final static String PRODUCT_WHITE_LIST = "/webshop/products*"; // ok
    private final static String ADMIN_PRODUCT_WHITE_LIST = "/webshop/products**";

    private final static String BASKET_WHITE_LIST = "/webshop/basket**"; //ok
    private final static String USER_ORDER_WHITE_LIST = "/webshop/order";
    private final static String ADMIN_ORDER_WHITE_LIST = "/webshop/orders**";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean //TODO check rights
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authz -> authz
                        // In memory database used during development
                        .requestMatchers("/h2-console/**").permitAll()
                        // Allow public access to authentication-related endpoints
                        .requestMatchers(POST,"/webshop/auth/*").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/webshop/products/**").permitAll()
                        // Access to the Basket for all logged in
                        .requestMatchers(GET, "/webshop/basket", "/webshop/products/*", "/webshop/user/", "").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(POST, "/webshop/orders").hasRole("ADMIN")
                        .requestMatchers(POST, BASKET_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(PUT, BASKET_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(DELETE, BASKET_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        // Access to Products based on authority
                        .requestMatchers(GET, PRODUCT_WHITE_LIST).permitAll()
                        .requestMatchers(GET, PRODUCT_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(POST, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        .requestMatchers(PUT, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        .requestMatchers(DELETE, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        // Access to User for all logged in
                        .requestMatchers(GET, USER_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        // Access to Order based on authority
                        .requestMatchers(GET, "webshop/order").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(POST, USER_ORDER_WHITE_LIST).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(GET, "webshop/orders**").hasRole("ADMIN")
                        // Require authentication for any other endpoint */
                        .anyRequest().permitAll()) // todo remove
//                        .anyRequest().authenticated())  // TODO Activate
                .headers((headers) ->        //TODO remove headers added just for using H2
                        headers
                                .frameOptions((frameOptions) -> frameOptions.disable())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                // Configure JWT authentication filter
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
