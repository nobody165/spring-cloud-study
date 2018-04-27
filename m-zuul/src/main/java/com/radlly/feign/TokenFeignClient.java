package com.radlly.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.radlly.feign.fallback.TokenFeignFallBack;
import com.radlly.model.AppObj;

@FeignClient(name = "m-security", fallback = TokenFeignFallBack.class)
public interface TokenFeignClient {
	
	
	@RequestMapping(value = "/tk/validate", method = RequestMethod.GET)
	public AppObj validate(@RequestParam("token") String token);
	
	@RequestMapping(value = "/tk/getInternalToken", method = RequestMethod.GET)
	public String getInternalToken();
	
//	@Component
//	static class HystrixClientFallbackFactory implements FallbackFactory<TokenFeignClient> {
//		private static Logger logger = LoggerFactory.getLogger(TokenFeignClient.class);
//		 public TokenFeignClient create(Throwable cause) {  
//		        HystrixClientFallbackFactory.logger.info("fallback reason was: {} " ,cause.getMessage());  
//		          
//		        return new TokenFeignClient()  
//		                {  
//		                    @Override  
//		                    public AppObj validate(String token) {  
//		                    	AppObj appObj = new AppObj();  
//		                    	appObj.setCode(AppObj.FAIL);
//		                        return appObj;  
//		                    }  
//		                };  
//		    }  
//	}
	
	
	
	
	
	
  
  
}
