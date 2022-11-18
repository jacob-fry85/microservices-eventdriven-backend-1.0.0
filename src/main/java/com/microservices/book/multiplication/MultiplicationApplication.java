package com.microservices.book.multiplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiplicationApplication.class, args);
    }
}

//read untill page 378