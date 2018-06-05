package com.radlly.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BaseObject {
	
	private long uuid;
	
	private Object obj;
	
	/**
	 * use for mysql insert
	 */
	private String jsonObj;
	
	private int pageStart = 0;
	
	private int pageEnd = 10;	

}
