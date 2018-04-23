package com.radlly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.radlly.feign"})
@EnableDiscoveryClient
@EnableCaching 
@MapperScan("com.radlly.mapper") 
public class MicProviderUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicProviderUserApplication.class, args);
	}

//	@Value("${http.port}")
//	private Integer port;
//
//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//		tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
//		return tomcat;
//	}
//
//	// 配置http
//	private Connector createStandardConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setPort(port);
//		return connector;
//	}
	
//	 @Bean  
//    public FilterRegistrationBean jwtFilterRegistrationBean(){  
//    FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
//    registrationBean.setFilter(new InternalTokenFilter());  
//    return registrationBean;  
//}  
	
	

}
