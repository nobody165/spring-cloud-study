package com.radlly;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


@SpringBootApplication 
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.radlly.mapper") 
public class ElevatorApplication {
	 public static void main(String[] args) {
		    SpringApplication.run(ElevatorApplication.class, args);
	 }
	@Bean  
    public static PropertySourcesPlaceholderConfigurer properties() {  
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();  
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();  
        yaml.setResources(new ClassPathResource("evAttributes.yml"));//File引入  
        configurer.setProperties(yaml.getObject());  
        return configurer;  
    } 
}
