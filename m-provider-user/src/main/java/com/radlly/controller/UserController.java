package com.radlly.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.radlly.constants.CommonConstants;
import com.radlly.model.AppObj;
import com.radlly.model.User;
import com.radlly.service.IUserService;

import io.swagger.annotations.ApiParam;

@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;	

    
	@PostMapping("/user/insert")
	@ResponseBody
	public AppObj insert(@ApiParam(required = true, name="phoneNumber", value = "电话号码")
					   @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
					   @ApiParam(required = true, name="password", value = "密码")
					   @RequestParam(value = "password", required = false) String password
			) {
		logger.info("用户注册!");
		// user.setPassword(CommonUtils.encrypt(user.getPassword()));
		User u = new User();
		u.setPhoneNumber(phoneNumber);
		u.setPassword(password);
		userService.insert(u);
		return new AppObj(AppObj.SSUCCESS,"user add successful!");
	}

//	@ApiOperation(value="获取用户信息", notes="")// 使用该注解描述接口方法信息  
//    @ApiImplicitParams({  
//            @ApiImplicitParam(name = "uuid", value = "用户ID", required = true, dataType = "", 
//            		paramType="path")  
//    })// 使用该注解描述方法参数信息，此处需要注意的是paramType参数，需要配置成path，否则在UI中访问接口方法时，会报错 
	//paramType表示参数的类型，可选的值为"path","body","query","header","form"
	@GetMapping("/get/{uuid}")
	@ResponseBody
	public AppObj get(@PathVariable Integer uuid) {
		logger.info("获取用户信息!uuid:"+uuid);
		// user.setPassword(CommonUtils.encrypt(user.getPassword()));
		User user = userService.get(uuid);
		if(null!=user)return new AppObj(user);
		return new AppObj("user not found!");
	}
	
//	@ApiOperation(value="获取用户信息", notes="")// 使用该注解描述接口方法信息  
//    @ApiImplicitParams({  
//            @ApiImplicitParam(name = "uuid", value = "用户ID", required = true, dataType = "", 
//            		paramType="path")  
//    })// 使用该注解描述方法参数信息，此处需要注意的是paramType参数，需要配置成path，否则在UI中访问接口方法时，会报错 
	//paramType表示参数的类型，可选的值为"path","body","query","header","form"

	  @HystrixCommand(fallbackMethod = "getByNameFallback", commandProperties = {
			  @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000"),
			  @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="10000")
	  },threadPoolProperties= {
			  @HystrixProperty(name="coreSize",value="1"),
			  @HystrixProperty(name="maxQueueSize",value="10")
	  })	
		@PostMapping("/getByName")
		@ResponseBody
		public AppObj getByName(@RequestParam  String username,@RequestParam String password) {
//			loginTokenFeignClient.getInternalToken();
		  
		  ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
			        .getRequestAttributes();
			        HttpServletRequest request = attributes.getRequest();
			        String internalToken= request.getHeader(CommonConstants.TOKEN_INTERNAL);
			        if(!StringUtils.isBlank(internalToken)) {
			        	logger.debug("in getByName::internal token is "+internalToken);
			        }
			User user = userService.getByName(username);		
			if(null!=user) {
				if(password.equals(user.getPassword())){
					return new AppObj(user);
				}
				else{
					return new AppObj("password is not correct!");
				}
				
			}
			return new AppObj("user not found!");
		}
	  
	  public AppObj getByNameFallback(String username,String password){
		  return new AppObj("user not found!");
	  }
	@GetMapping("/del/{uuid}")
	@ResponseBody
	public AppObj del(@PathVariable Integer uuid) {
		logger.info("获取用户信息!uuid:"+uuid);
		// user.setPassword(CommonUtils.encrypt(user.getPassword()));
		 userService.delete(uuid);
		return new AppObj(AppObj.SSUCCESS,"delete user successful!");
	}
}
