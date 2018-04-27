package com.radlly.feign.fallback;

import org.springframework.stereotype.Component;

import com.radlly.feign.UserFeignClient;
import com.radlly.model.AppObj;

@Component
public class UserFeignFallBack implements UserFeignClient{

	@Override
	public AppObj get(Integer id) {
		// TODO Auto-generated method stub
		return new AppObj("get user provider error. please see the log!");
	}

	@Override
	public AppObj getByName(String username, String password) {
		// TODO Auto-generated method stub
		return new AppObj("get user provider error. please see the log!");
	}

}
