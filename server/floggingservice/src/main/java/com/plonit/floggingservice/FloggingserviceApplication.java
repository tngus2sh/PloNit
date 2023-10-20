package com.plonit.floggingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FloggingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FloggingserviceApplication.class, args);
	}

}
