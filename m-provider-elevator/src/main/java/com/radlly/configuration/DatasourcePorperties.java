package com.radlly.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration  
public class DatasourcePorperties {
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.initialSize}")
	private String initialSize;
	@Value("${spring.datasource.minIdle}")
	private String minIdle;
	@Value("${spring.datasource.maxWait}")
	private String maxWait;
	@Value("${spring.datasource.maxActive}")
	private String maxActive;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;
	
	
}
