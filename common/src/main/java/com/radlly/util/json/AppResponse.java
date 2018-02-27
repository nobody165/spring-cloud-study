package com.radlly.util.json;

import java.io.Serializable;

public class AppResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int CODESTATUSSUCCESS=0;
	public static final int CODESTATUSFAIL=1;
	//1是错误 0是成功 -1失效
	private int code;
	private String message;
	private Object obj;
	//秘钥码
	private String tokenStr;

	public AppResponse(){}

	public AppResponse(int code){
		this.code = code;
	}

	public AppResponse(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	public String getTokenStr() {
		return tokenStr;
	}
	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}
	@Override
	public String toString() {
		return "AppJson [code=" + code + ", message=" + message + ", obj="
				+ obj + "]";
	}
	
	
}
