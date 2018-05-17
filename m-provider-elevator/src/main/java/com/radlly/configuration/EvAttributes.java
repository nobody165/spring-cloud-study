package com.radlly.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ev")
@Data
public class EvAttributes {
	
	 private Map<String, String> attributes = new HashMap<String, String>();  

}
