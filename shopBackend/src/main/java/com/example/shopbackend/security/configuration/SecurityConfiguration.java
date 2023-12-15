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

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    private final static String USER_WHITE_LIST = "webshop/user**";
    private final static String PRODUCT_WHITE_LIST = "webshop/products*";
    private final static String ADMIN_PRODUCT_WHITE_LIST = "webshop/products**";
    private final static String AUTHORIZATION_WHITE_LIST = "/webshop/auth/**";
    private final static String BASKET_WHITE_LIST = "webshop/basket*";
    private final static String USER_ORDER_WHITE_LIST = "webshop/order*";
    private final static String ADMIN_ORDER_WHITE_LIST = "webshop/order**";


    @Bean //TODO check rights
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{

        http.authorizeHttpRequests(authz -> authz
                        // In memory database used during development
                        .requestMatchers("/h2-console/**").permitAll()
                        // Allow public access to authentication-related endpoints
                        .requestMatchers(AUTHORIZATION_WHITE_LIST).permitAll()
//                        .requestMatchers(HttpMethod.GET,"/webshop/products/**").permitAll()
                        // Access to the Basket for all logged in
                        .requestMatchers(HttpMethod.GET, BASKET_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, BASKET_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PUT, BASKET_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, BASKET_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        // Access to Products based on authority
                        .requestMatchers(HttpMethod.GET, PRODUCT_WHITE_LIST).permitAll()
                        .requestMatchers(HttpMethod.GET, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN") // admin can look at deleted items
                        .requestMatchers(HttpMethod.POST, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, ADMIN_PRODUCT_WHITE_LIST).hasRole("ADMIN")
                        // Access to User for all logged in
                        .requestMatchers(HttpMethod.GET, USER_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        // Access to Order based on authority
                        .requestMatchers(HttpMethod.GET, USER_ORDER_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, USER_ORDER_WHITE_LIST).hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, ADMIN_ORDER_WHITE_LIST).hasRole("ADMIN")
                        // Require authentication for any other endpoint */
                        .anyRequest().authenticated())
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
