package com.radlly.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.radlly.interceptors.DBJsonDataInterceptor;

@Configuration
public class MybatisPlusConfig {
	
	@Bean
    public DBJsonDataInterceptor dBJsonDataInterceptor() {
        return new DBJsonDataInterceptor();
    }
}
