package com.radlly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class MicSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicSecurityApplication.class, args);
	}
	
//	 @Bean  
//    public FilterRegistrationBean internalFilterRegistrationBean(){  
//    FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
//    registrationBean.setFilter(new InternalTokenFilter());  
//    registrationBean.setEnabled(false);
//    return registrationBean;  
//}  
	
	

}
