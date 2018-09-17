package com.radlly.exception;

public class RadllyExceptionErrorCode extends Throwable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String message;
	
	
	
	public RadllyExceptionErrorCode(String key, String message) {
		super();
		this.key = key;
		this.message = message;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
