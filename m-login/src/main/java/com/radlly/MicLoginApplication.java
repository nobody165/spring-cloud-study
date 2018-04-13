package com.radlly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class MicLoginApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(MicLoginApplication.class, args);
	}

//	  @Bean
//	  @LoadBalanced
//	  public RestTemplate restTemplate() {
//	    return new RestTemplate();
//	  }
//	@Value("${http.port}")
//	private Integer port;

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

}
