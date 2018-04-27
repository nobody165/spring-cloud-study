package com.radlly.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.radlly.feign.fallback.UserFeignFallBack;
import com.radlly.model.AppObj;

@FeignClient(name = "m-provider-user", fallback = UserFeignFallBack.class)
public interface UserFeignClient {
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,consumes = "application/json")
	public AppObj get(@PathVariable("id") Integer id) ;
	
	@RequestMapping(value = "/getByName", method = RequestMethod.POST,consumes = "application/json")
	public AppObj getByName(@RequestParam("username") String username,@RequestParam("password") String password);
  
  
}
