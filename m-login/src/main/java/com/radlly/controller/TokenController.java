package com.radlly.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.radlly.configuration.TokenConfiguration;
import com.radlly.model.AppObj;
import com.radlly.model.InternalObj;
import com.radlly.service.TokenRepository;
import com.radlly.utils.JwtTokenUtil;
import com.radlly.utils.jwt.IJWTInfo;

@RestController
@RequestMapping("tk")
public class TokenController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private TokenConfiguration tokenConfiguration;

	@HystrixCommand(fallbackMethod = "getUserInfoFromTokenFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "1"),
					@HystrixProperty(name = "maxQueueSize", value = "10") })

	@GetMapping("/get")
	@ResponseBody
	public Map<String, String> getUserInfoFromToken(@RequestParam String token) {
		Map<String, String> user = new HashMap<String, String>();
		try {
			IJWTInfo info = jwtTokenUtil.getInfoFromToken(token);
			if (null != info.getUniqueName()) {
				user.put("username", info.getUniqueName());
			}
			if (null != info.getId()) {
				user.put("id", info.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	public Map<String, String> getUserInfoFromTokenFallback(String token) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("ERROE", "无法解析！");
		return error;
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 * @param name
	 * @return
	 */
	@GetMapping("/validate")
	@ResponseBody
	public AppObj validate(@RequestParam String token) {
		AppObj appObj = new AppObj(AppObj.SSUCCESS);
		if (StringUtils.isNotBlank(token)) {
			if (!loginTokenValidate(token)) {
				appObj.setCode(AppObj.FAIL);
				appObj.setMessage("token is vaild!");
			}
		}
		if(appObj.getCode()==AppObj.SSUCCESS) {
			InternalObj internalObj = new InternalObj();
			internalObj.setInternalTokenName(this.getInternalTokenName());
			internalObj.setInternalToken(this.getInternalToken());
			appObj.setObj(internalObj);
		}		
		return appObj;
	}

	/**
	 * 内部token验证
	 * 
	 * @param token
	 * @return
	 */
	private boolean internalTokenValidate(String token) {
		if (null == stringRedisTemplate.opsForHash().get(tokenConfiguration.getInternalName(), token)) {
			return false;
		}
		return true;
	}

	/**
	 * 登录token验证
	 * 
	 * @param token
	 * @return
	 */
	private boolean loginTokenValidate(String token) {
		IJWTInfo info = null;
		try {
			info = jwtTokenUtil.getInfoFromToken(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (null == info.getId())
			return false;
		String a = stringRedisTemplate.opsForValue().get(info.getId());
		if (!token.equals(a)) {
			return false;
		}
		return true;
	}

	@GetMapping("/getLoginTokenName")
	@ResponseBody
	public String getLoginTokenName() {
		return tokenConfiguration.getLoginName();
	}

	@GetMapping("/getInternalTokenName")
	@ResponseBody
	public String getInternalTokenName() {
		return tokenConfiguration.getInternalName();
	}

	@GetMapping("/getInternalToken")
	@ResponseBody
	public String getInternalToken() {
		return (String) stringRedisTemplate.opsForHash().get(tokenConfiguration.getInternalName(), "0");
	}

	@GetMapping("/getAllInternalToken")
	@ResponseBody
	public List<String> getAllInternalToken() {
		List<String> result = new ArrayList<String>();
		List<Object> reslutMapList = stringRedisTemplate.opsForHash().values(tokenConfiguration.getInternalName());
		reslutMapList.forEach((Object obj) -> {
			result.add((String) obj);
		});
		return result;
	}
}
