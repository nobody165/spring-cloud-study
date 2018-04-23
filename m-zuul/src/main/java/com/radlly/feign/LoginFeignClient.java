package com.radlly.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.radlly.feign.fallback.LoginFeignFallBack;
import com.radlly.model.AppObj;

import feign.hystrix.FallbackFactory;

@FeignClient(name = "m-login", fallback = LoginFeignFallBack.class)
public interface LoginFeignClient {
	
	
	@RequestMapping(value = "/tk/validate", method = RequestMethod.GET)
	public AppObj validate(@RequestParam("token") String token);
	
	@Component
	static class HystrixClientFallbackFactory implements FallbackFactory<LoginFeignClient> {
		private static Logger logger = LoggerFactory.getLogger(LoginFeignClient.class);
		 public LoginFeignClient create(Throwable cause) {  
		        HystrixClientFallbackFactory.logger.info("fallback reason was: {} " ,cause.getMessage());  
		          
		        return new LoginFeignClient()  
		                {  
		                    @Override  
		                    public AppObj validate(String token) {  
		                    	AppObj appObj = new AppObj();  
		                    	appObj.setCode(AppObj.FAIL);
		                        return appObj;  
		                    }  
		                };  
		    }  
	}
	
	
	
	
	
	
  
  
}
