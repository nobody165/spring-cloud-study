package com.radlly.runner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.radlly.configuration.TokenConfiguration;

@Component
public class InternalTokenRunner implements CommandLineRunner {

	private static final int PIECE_OF_REFRESH=5;
	
	private static final int REFRESH_MIN=30;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	
	@Autowired
    private TokenConfiguration tokenConfiguration;
	public static String genToken(){
		return "INTERNAL_TOKEN::" + System.currentTimeMillis();
	}
	
	/**
	 * 初始化internal token
	 */
	@Override
	public void run(String... args) throws Exception {
			Map<String,String> map = new HashMap<String,String>();
		 if (!redisTemplate.hasKey(tokenConfiguration.getInternalName())) {
			 int pieces = REFRESH_MIN/PIECE_OF_REFRESH;
			 map.put("0", genToken());
			 for(int i =1;i<pieces-1;i++) {
				 map.put(String.valueOf(i), genToken());
			 }
			 redisTemplate.opsForHash().putAll(tokenConfiguration.getInternalName(),map);  
		 }
	}
	
	@Scheduled(cron = "0 0/1 * * * ?")
    public void refreshToken(){
		Map<Object, Object> resultMapSet = redisTemplate.opsForHash().entries(tokenConfiguration.getInternalName()); 
		Map<String,String> map = new HashMap<String,String>();
		map.put("0", genToken());
		int lastOne = REFRESH_MIN/PIECE_OF_REFRESH-2;
		for(int i=0;i<lastOne;i++) {
			String a = (String) resultMapSet.get(String.valueOf(i));			
			map.put(String.valueOf(i+1), a);
		}
		redisTemplate.opsForHash().putAll(tokenConfiguration.getInternalName(),map);  
		
    }
}
