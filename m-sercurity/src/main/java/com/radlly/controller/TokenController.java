package com.radlly.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.radlly.configuration.TokenConfiguration;
import com.radlly.constants.CommonConstants;
import com.radlly.model.AppObj;
import com.radlly.service.TokenRepository;
import com.radlly.utils.JwtTokenUtil;
import com.radlly.utils.jwt.IJWTInfo;
import com.radlly.utils.jwt.JWTInfo;

@RestController
@RequestMapping("tk")
public class TokenController {

	private static Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private TokenConfiguration tokenConfiguration;

//	@HystrixCommand(fallbackMethod = "getUserInfoFromTokenFallback", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
//			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") }, threadPoolProperties = {
//					@HystrixProperty(name = "coreSize", value = "1"),
//					@HystrixProperty(name = "maxQueueSize", value = "10") })
	@GetMapping("/get")
	@ResponseBody
	public AppObj getUserInfoFromToken(@RequestParam String token) {
		
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

		return new AppObj(user);
	}

	public AppObj getUserInfoFromTokenFallback(String token) {
		return new AppObj("usr parsing hystrix error!");
	}

	/**
	 * 验证token,成功后返回internal token
	 * 
	 * @param token
	 * @param name
	 * @return
	 */
//	@HystrixCommand(fallbackMethod = "validateFallback")
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
//			InternalObj internalObj = new InternalObj();
//			internalObj.setInternalTokenName(this.getInternalTokenName());
//			internalObj.setInternalToken(this.getInternalToken());
			appObj.setObj(this.getInternalToken());
		}		
		return appObj;
	}
	
	public AppObj validateFallback(String token) {
		return new AppObj("token validate hystrix error!");
	}

	/**
	 * 内部token验证
	 * 
	 * @param token
	 * @return
	 */
//	@HystrixCommand(fallbackMethod = "internalTokenValidateFallback")
	@GetMapping("/internalTokenValidate")
	@ResponseBody
	public AppObj internalTokenValidate(String token) {
		if (null == stringRedisTemplate.opsForHash().get(tokenConfiguration.getInternalName(), token)) {
			return new AppObj(AppObj.FAIL);
		}
		return new AppObj(AppObj.SSUCCESS);
	}
	
	public AppObj internalTokenValidateFallback(String token) {
		return new AppObj("internal token validate hystrix error!");
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
//	@HystrixCommand(fallbackMethod = "saveTokenFallback")
	@PostMapping("/saveToken")
	@ResponseBody
	public AppObj saveToken(String key,String value) {
		AppObj appObj = new AppObj(AppObj.SSUCCESS);
		String token;
		try {
			token = tokenRepository.save(key, jwtTokenUtil.generateToken(new JWTInfo(key,value)));
		} catch (Exception e) {
			e.printStackTrace();
			appObj.setCode(AppObj.FAIL);
			appObj.setMessage("save token error!");
			return appObj;
		}
		appObj.setObj(token);
		return appObj;
	}
	
	
	public AppObj saveTokenFallback(String key,String value) {
		return new AppObj("save token hystrix error!");
	}	
	
	@GetMapping("/removeToken")
	@ResponseBody
	public AppObj removeToken(String token) {
		AppObj appObj = new AppObj(AppObj.SSUCCESS);
		try {
			if(validate(token).getCode()==AppObj.SSUCCESS) {
				IJWTInfo info = jwtTokenUtil.getInfoFromToken(token);
				if (null != info.getId()) {
					String key = CommonConstants.LOGIN_TOKEN+info.getId();
					tokenRepository.remove(key);
					appObj.setMessage("remove token success");
				}
			}else {
				appObj.setCode(AppObj.FAIL);
				appObj.setMessage("token is already clear!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appObj.setCode(AppObj.FAIL);
			appObj.setMessage("remove token error!");
			return appObj;
		}		
		return appObj;
	}
	
	public AppObj removeTokenFallback(String token) {
		return new AppObj("remove token hystrix error!");
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
		String a = stringRedisTemplate.opsForValue().get(CommonConstants.LOGIN_TOKEN+info.getId());
		if (!token.equals(a)) {
			return false;
		}
		return true;
	}
}
