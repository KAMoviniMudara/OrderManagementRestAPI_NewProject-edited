package com.example.ordermanagementrestapi;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OrderManagementRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementRestApiApplication.class, args);
        Logger logger = LoggerFactory.getLogger(OrderManagementRestApiApplication.class);
        logger.info("Hello, SLF4J!");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
