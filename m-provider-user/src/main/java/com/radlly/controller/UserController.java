package com.radlly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.radlly.model.User;
import com.radlly.service.IUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;

	@ApiOperation(value="新增用户", notes = "电话号码注册")// 使用该注解描述接口方法信息  
	@PostMapping("/user/insert")
	@ResponseBody
	public Long insert(@ApiParam(required = true, name="phoneNumber", value = "电话号码")
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
		return u.getUuid();
	}

	@ApiOperation(value="获取用户信息", notes="")// 使用该注解描述接口方法信息  
    @ApiImplicitParams({  
            @ApiImplicitParam(name = "uuid", value = "用户ID", required = true, dataType = "", 
            		paramType="path")  
    })// 使用该注解描述方法参数信息，此处需要注意的是paramType参数，需要配置成path，否则在UI中访问接口方法时，会报错 
	//paramType表示参数的类型，可选的值为"path","body","query","header","form"
	@GetMapping("/get/{uuid}")
	@ResponseBody
	public User get(@PathVariable Integer uuid) {
		logger.info("获取用户信息!uuid:"+uuid);
		// user.setPassword(CommonUtils.encrypt(user.getPassword()));
		User user = userService.get(uuid);
		return user;
	}
	@GetMapping("/del/{uuid}")
	@ResponseBody
	public boolean del(@PathVariable Integer uuid) {
		logger.info("获取用户信息!uuid:"+uuid);
		// user.setPassword(CommonUtils.encrypt(user.getPassword()));
		 userService.delete(uuid);
		return true;
	}
}
