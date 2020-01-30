package com.cmr.dea.sellerapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class SellerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerApiApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate gerRestTemplate(){
        return new RestTemplate();
    }
}
