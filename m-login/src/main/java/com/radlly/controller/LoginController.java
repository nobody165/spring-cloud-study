package com.radlly.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.radlly.feign.UserFeignClient;
import com.radlly.model.AppObj;
import com.radlly.model.User;
import com.radlly.service.TokenRepository;
import com.radlly.utils.JsonUtils;
import com.radlly.utils.JwtTokenUtil;
import com.radlly.utils.jwt.IJWTInfo;
import com.radlly.utils.jwt.JWTInfo;

@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	  @Autowired
	  private UserFeignClient userFeignClient;
	  @Autowired
	  private JwtTokenUtil jwtTokenUtil;
	  @Autowired
	  private TokenRepository tokenRepository;

	  
	  @HystrixCommand(fallbackMethod = "loginFallback", commandProperties = {
			  @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000"),
			  @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="10000")
	  },threadPoolProperties= {
			  @HystrixProperty(name="coreSize",value="1"),
			  @HystrixProperty(name="maxQueueSize",value="10")
	  })	 
	  @PostMapping("/login")
	  @ResponseBody
	  public AppObj login(@RequestParam String username,@RequestParam String password) {
		 String token;
		 AppObj user = userFeignClient.getByName(username, password);
	     if(user.getCode()==AppObj.SSUCCESS) {	    	 
	    	 User u = JsonUtils.fromBean(JsonUtils.BeanToJson(user.getObj()), User.class);
	    	 try {
				token = tokenRepository.save(String.valueOf(u.getUuid()), jwtTokenUtil.generateToken(new JWTInfo(username,String.valueOf(u.getUuid()))));
				user.setObj(token);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				user.setMessage("server storage is vaild, please contract administrator!");
			}
	     }	    
	     return user;
	  }
	  
	  public AppObj loginFallback(String username,String password){
		  return new AppObj("user not found!");
	  }
	  
	  
	  @PostMapping("/logout")
	  @ResponseBody
	  public AppObj logout(@RequestHeader HttpHeaders headers) throws Exception {
		  List<String> token =headers.get(IJWTInfo.TOKEN);
		  IJWTInfo u = jwtTokenUtil.getInfoFromToken(token.get(0));
		  tokenRepository.remove(u.getId());
	     return new AppObj("logout successful");
	  }
	  

}
