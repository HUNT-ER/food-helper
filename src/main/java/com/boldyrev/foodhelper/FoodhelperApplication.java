package com.boldyrev.foodhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class FoodhelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodhelperApplication.class, args);
    }

}
