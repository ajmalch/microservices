package com.example.domains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableCaching
public class DomainsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainsApplication.class, args);
    }

}
