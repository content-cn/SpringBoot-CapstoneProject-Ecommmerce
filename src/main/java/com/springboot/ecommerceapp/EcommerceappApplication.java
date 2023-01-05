package com.springboot.ecommerceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EcommerceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceappApplication.class, args);
	}

}
