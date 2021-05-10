package com.adminpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@PropertySource("classpath:application.yml")
@EnableAsync
@SpringBootApplication
public class AdminproApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminproApplication.class, args);
    }

}
