package com.radlly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class MicLoginApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MicLoginApplication.class, args);
	}

}
