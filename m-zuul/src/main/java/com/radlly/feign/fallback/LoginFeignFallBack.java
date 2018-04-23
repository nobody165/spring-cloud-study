package com.radlly.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.radlly.feign.LoginFeignClient;
import com.radlly.model.AppObj;

@Component
public class LoginFeignFallBack implements LoginFeignClient{
	private static Logger logger = LoggerFactory.getLogger(LoginFeignFallBack.class);

	@Override
	public AppObj validate(String token) {
		logger.debug("validation server donw...");
		return new AppObj(AppObj.FAIL);
	}
	

}
