package com.radlly.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class TokenConfiguration {
	@Value("${token.internal.name}")
	private String internalName;
	
	@Value("${token.login.name}")
	private String loginName;
}
