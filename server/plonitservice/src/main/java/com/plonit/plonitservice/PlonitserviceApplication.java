package com.plonit.plonitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PlonitserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlonitserviceApplication.class, args);
	}

}
