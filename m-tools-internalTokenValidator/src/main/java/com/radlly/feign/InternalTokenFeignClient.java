package com.radlly.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radlly.feign.fallback.InternalTokenFeignFallBack;

@FeignClient(name = "m-security",fallback = InternalTokenFeignFallBack.class )
public interface InternalTokenFeignClient {
	@RequestMapping(value = "/tk/getAllInternalToken", method = RequestMethod.GET)
	public List<String> getAllInternalToken();
	
	@RequestMapping(value = "/tk/getInternalToken", method = RequestMethod.GET)
	public String getInternalToken();
	
	
  
}
