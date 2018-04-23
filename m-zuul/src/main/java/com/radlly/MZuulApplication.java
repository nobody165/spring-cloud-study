package com.radlly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableZuulProxy
public class MZuulApplication {

	public static void main(String[] args){
		SpringApplication.run(MZuulApplication.class, args);
	}
}
