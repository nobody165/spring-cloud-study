package com.radlly;
import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.radlly.configuration.SnowFlakeIdFactory;


@SpringBootApplication 
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.radlly.mapper")
@ComponentScan(basePackages = {"com.radlly.*"})
public class ElevatorApplication {
	 public static void main(String[] args) {
		 ApplicationContext  ctx = SpringApplication.run(ElevatorApplication.class, args);
		 String[] beanNames =  ctx.getBeanNamesForAnnotation(Component.class);

	       System.out.println("Service注解beanNames个数："+beanNames.length);

	       for(String bn:beanNames){

	           System.out.println(bn);

	       }
	 }
//		@Bean  
//	    public static PropertySourcesPlaceholderConfigurer properties() {  
//	        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();  
//	        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();  
//	        yaml.setResources(new ClassPathResource("evAttributes.yml"));//File引入  
//	        configurer.setProperties(yaml.getObject());  
//	        return configurer;  
//	    } 

		
		@Bean  
	    public static SnowFlakeIdFactory snowFlakeIdFactory() { 	       
	        return new SnowFlakeIdFactory(1, 1);  
	    } 
		
		@Bean  
		@Qualifier("batchJdbcTemplate")
	    public static JdbcTemplate batchJdbcTemplate(@Qualifier("druidDataSource")DataSource dataSource){
			
			 return new JdbcTemplate(dataSource); 
				
	        
	    } 



}
