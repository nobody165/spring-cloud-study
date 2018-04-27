package com.radlly.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class KeyConfiguration {
	@Value("${jwt.rsa-secret}")
	private String userSecret;
	@Value("${client.rsa-secret}")
	private String serviceSecret;
	private byte[] userPubKey;
	private byte[] userPriKey;
	private byte[] servicePriKey;
	private byte[] servicePubKey;
}
