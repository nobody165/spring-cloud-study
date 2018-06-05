package com.radlly.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppObj implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SSUCCESS=0;
	public static final int FAIL=1;
	
	private int code;
	
	private String message;
	
	private Object obj;
	
	

	public AppObj(Object obj) {
		super();
		this.code = SSUCCESS;
		this.obj = obj;
	}
	
	public AppObj(String message) {
		super();
		this.code = FAIL;
		this.message = message;
	}
	
	public AppObj(Integer code,String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public AppObj(Integer code) {
		super();
		this.code = code;
	}

	public AppObj() {
		super();
		// TODO Auto-generated constructor stub
	}

}
