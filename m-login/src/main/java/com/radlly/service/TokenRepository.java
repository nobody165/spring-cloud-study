package com.radlly.service;

public interface TokenRepository {
	
	public final static Long TOKEN_EXPIRE_MIN=3L;

	String save(String key,String value);
	
	String get(String key);
	
	void remove(String key);
	
	
	
}
