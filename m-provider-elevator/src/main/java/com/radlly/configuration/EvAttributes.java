package com.radlly.configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ev")
@Data
public class EvAttributes {
	
	private static final String SPLITER ="_";
	
	private Map<String, String> attributes = new TreeMap<String, String>();  	
	
	
	public Map<String, String> getOrderedMap(){
		Map<String, String> result = new LinkedHashMap<String,String>();
		attributes.forEach((k,v)->{
			result.put(k.split(SPLITER)[1], v);
		});
		
		return result;
	}
	 
}
