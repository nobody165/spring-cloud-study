package com.radlly.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

	  @Value("${swagger.document.basePackage}")
	  private String swaggerBasePackage;

	  @Value("${swagger.document.title}")
	  private String swaggerTitle;

	  @Value("${swagger.document.version}")
	  private String swaggerVersion;

	  @Bean
	  public Docket testApi()
	  {
	    ParameterBuilder parameterBuilder = new ParameterBuilder();
//	    parameterBuilder.name("tokenStr")
//	      .description("tokenStr")
//	      .modelRef(new ModelRef("string"))
//	      .parameterType("header")
//	      .required(false)
//	      .build();
//	    List list = Lists.newArrayList(new Parameter[] { parameterBuilder.build() });

	    return new Docket(DocumentationType.SWAGGER_2)
//	      .globalOperationParameters(list)
	      .apiInfo(apiInfo())
	      .select()
	      .apis(RequestHandlerSelectors.basePackage(this.swaggerBasePackage))
	      .paths(PathSelectors.any())
	      .build();
	  }

	  private ApiInfo apiInfo()
	  {
	    return new ApiInfoBuilder()
	      .title(this.swaggerTitle)
	      .version(this.swaggerVersion)
	      .build();
	  }
}
