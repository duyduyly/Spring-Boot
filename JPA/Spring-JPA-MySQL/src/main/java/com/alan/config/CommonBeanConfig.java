package com.alan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeanConfig {

    @Bean
    public ObjectMapper createObMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Faker createFaker() {
        return new Faker();
    }

}
