package com.radlly.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.radlly.configuration.KeyConfiguration;
import com.radlly.utils.jwt.IJWTInfo;
import com.radlly.utils.jwt.JWTHelper;
import com.radlly.utils.jwt.JWTInfo;
import com.radlly.utils.jwt.RsaKeyHelper;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JwtTokenUtil {	

    @Value("${jwt.expire}")
    private int expire;
    @Autowired
    private KeyConfiguration keyConfiguration;

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
    	if(null==keyConfiguration.getUserPriKey()) {
    		keyConfiguration.setUserPriKey(RsaKeyHelper.generateKey(keyConfiguration.getUserSecret()).get("pri"));
    	}
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getUserPriKey(),expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
    	if(null==keyConfiguration.getUserPubKey()) {
    		keyConfiguration.setUserPubKey(RsaKeyHelper.generateKey(keyConfiguration.getUserSecret()).get("pub"));
    	}
        return JWTHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
    }
    
 
    
    public static void main(String[] args) throws Exception {
    	JwtTokenUtil util = new JwtTokenUtil();
    	util.expire = 14400;
    	 KeyConfiguration keyConfiguration = new KeyConfiguration();
    	 keyConfiguration.setUserSecret("123456");
    	 util.keyConfiguration = keyConfiguration;
    	 JWTInfo jwtInfo = new JWTInfo("aa","1");
    	String token = util.generateToken(jwtInfo);
    	System.out.println("token:"+token);
    	IJWTInfo jwtInfo1 = util.getInfoFromToken("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhYSIsInVzZXJJZCI6IjEiLCJleHAiOjE1MjM5NDY0MTd9.X4nFFlIBq7TjHMg2DF2dpfU2BhDakeSECr9ubjcRtV8LANYi7G1zLndtB5Kjsa0yy26UUeHuMnkzUrc8dKdUP2luDQ7k2B-ailXdLVmx3DDUnX-ulXz2T466V8YIMo5JyAGA_Oyo5HY4_Bf0UFdIuRiup6_kcLgQNQSk13Whv08");
    	System.out.println("id::"+jwtInfo1.getId()+"==> name "+jwtInfo1.getUniqueName());
    }


}
