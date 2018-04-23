package com.radlly.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "m-login")
public interface TokenFeignClient {
	@RequestMapping(value = "/tk/get", method = RequestMethod.GET)
	public Map<String,String> get(@RequestParam("token") String token);
	
	@RequestMapping(value = "/tk/validate", method = RequestMethod.GET)
	public boolean validate(@RequestParam("token") String token,@RequestParam("name") String name);
	
	@RequestMapping(value = "/tk/getLoginTokenName", method = RequestMethod.GET)
	public String getLoginTokenName();
	
	@RequestMapping(value = "/tk/getInternalTokenName", method = RequestMethod.GET)
	public String getInternalTokenName();
	
	@RequestMapping(value = "/tk/getInternalToken", method = RequestMethod.GET)
	public String getInternalToken();
	
	@RequestMapping(value = "/tk/getAllInternalToken", method = RequestMethod.GET)
	public List<String> getAllInternalToken();
	
	
  
}
