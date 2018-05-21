package com.radlly.model;

import lombok.Data;

@Data
public class Location {
	
	public static final int CODE_SUCCESS = 0;//success
	public static final int CODE_FAILED = 1;//success
	
	private int code;
	private String msg;

	private Double lat;
	private Double lng;
	
}
