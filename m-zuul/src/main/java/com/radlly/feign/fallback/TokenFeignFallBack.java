package com.radlly.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.radlly.feign.TokenFeignClient;
import com.radlly.model.AppObj;

@Component
public class TokenFeignFallBack implements TokenFeignClient{
	private static Logger logger = LoggerFactory.getLogger(TokenFeignFallBack.class);

	@Override
	public AppObj validate(String token) {
		logger.debug("validation server donw...");
		return new AppObj(AppObj.FAIL);
	}

	@Override
	public String getInternalToken() {
		logger.debug("validation server donw...");
		return "get internal token fall....";
	}
	

}
