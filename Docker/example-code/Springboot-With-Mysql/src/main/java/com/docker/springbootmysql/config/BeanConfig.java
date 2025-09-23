package com.docker.springbootmysql.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Faker initializeFaker() {
        return new Faker();
    }
}
