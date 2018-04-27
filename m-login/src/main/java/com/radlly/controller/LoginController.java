package com.radlly.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.radlly.constants.CommonConstants;
import com.radlly.feign.TokenFeignClient;
import com.radlly.feign.UserFeignClient;
import com.radlly.model.AppObj;
import com.radlly.model.User;
import com.radlly.utils.JsonUtils;

@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	  @Autowired
	  private UserFeignClient userFeignClient;
	  @Autowired
	  private TokenFeignClient tokenFeignClient;
	  
	  

	  @GetMapping("/getInternalToken")
	  @ResponseBody
	  public String getInternalToken() {
		  ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
			        .getRequestAttributes();
			        HttpServletRequest request = attributes.getRequest();
			        String internalToken= request.getHeader(CommonConstants.TOKEN_INTERNAL);
	     return internalToken;
	  }
	  
	  @HystrixCommand(fallbackMethod = "loginFallback",
			  commandProperties = {
			  @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000"),
	  })
	  @PostMapping("/login")
	  @ResponseBody
	  public AppObj login(@RequestParam String username,@RequestParam String password) {
		 logger.debug("###in logincontroller.login");
		 AppObj user = userFeignClient.getByName(username, password);
		 AppObj token = new AppObj(AppObj.FAIL);
	     if(user.getCode()==AppObj.SSUCCESS) {	    	 
	    	 User u = JsonUtils.fromBean(JsonUtils.BeanToJson(user.getObj()), User.class);
	    	 try {
	    		  token = tokenFeignClient.saveToken(CommonConstants.LOGIN_TOKEN+String.valueOf(u.getUuid()), u.getUsername());
	    		  token.setMessage("login success.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				user.setMessage("server storage is vaild, please contract administrator!");
			}
	     }	   	     
	     return user;
	  }
	  
	  public AppObj loginFallback(String username,String password){
		  return new AppObj("login hystrix error!");
	  }
	  
	  
//	  @HystrixCommand(fallbackMethod = "logoutFallback")
	  @PostMapping("/logout")
	  @ResponseBody
	  public AppObj logout(@RequestHeader HttpHeaders headers) throws Exception {
		  List<String> tokens =headers.get(CommonConstants.TOKEN);
		  if(!tokens.isEmpty()) {
			  String token = tokens.get(0);
			 AppObj obj = tokenFeignClient.removeToken(token);
			 if(obj.getCode()==AppObj.FAIL) {
				 return obj;
			 }
		  }		
	     return new AppObj("logout successfully!");
	  }
	  public AppObj logoutFallback(HttpHeaders headers){
		  return new AppObj("logout hystrix error!");
	  }  

}
