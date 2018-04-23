package com.radlly.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.radlly.service.TokenRepository;
import com.radlly.utils.redisson.RedissLockUtil;

@Service
public class RedissonStore implements TokenRepository {

	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Override
	public String save(String key, String value) {
		RedissLockUtil.lock("lock_"+key, TimeUnit.MINUTES, 1);
//		RBucket<String> keyObject = redissonClient.getBucket(key);
		String keyObject = stringRedisTemplate.opsForValue().get(key);
		if(!StringUtils.isBlank(keyObject))return keyObject;
//		keyObject.set(value, TOKEN_EXPIRE_MIN, TimeUnit.MINUTES);
		stringRedisTemplate.opsForValue().set(key, value,TOKEN_EXPIRE_MIN, TimeUnit.MINUTES);
		RedissLockUtil.unlock("lock_"+key);
		return value;
	}

	@Override
	public String get(String key) {
		return redissonClient.getBucket(key).get().toString();
	}

	@Override
	public void remove(String key) {
		redissonClient.getBucket(key).delete();
	}
	

}
