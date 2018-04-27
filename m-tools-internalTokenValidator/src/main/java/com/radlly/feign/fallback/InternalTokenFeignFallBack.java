package com.radlly.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.radlly.feign.InternalTokenFeignClient;

@Component
public class InternalTokenFeignFallBack implements InternalTokenFeignClient{
	private static Logger logger = LoggerFactory.getLogger(InternalTokenFeignFallBack.class);
	@Override
	public List<String> getAllInternalToken() {
		logger.debug(" getAllInternalToken fallback..");
		return null;
	}

	@Override
	public String getInternalToken() {
		logger.debug(" getInternalToken fallback..");
		return null;
	}

}
