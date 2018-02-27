package com.radlly.util.token;

public class TokenUtil {
	public static final String INTERNAL_TOKEN = "internal";
	
	public static boolean isInternal(String token){
		if(token.equals(INTERNAL_TOKEN))return true;
		else return false;
		
	}
}
