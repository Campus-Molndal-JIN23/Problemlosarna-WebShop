package com.example.shopbackend.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class config {
    @Bean
    Mapper mappar(){
        return new DozerBeanMapper();
    }
}
