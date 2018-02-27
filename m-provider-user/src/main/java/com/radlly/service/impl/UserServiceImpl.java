package com.radlly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.radlly.mapper.UserMapper;
import com.radlly.model.User;
import com.radlly.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
 	private UserMapper userMapper;

	public int insert(User user) {
		return userMapper.insert(user);		
	}

//	@Cacheable(value="users#${select.cache.timeout:3600}#${select.cache.refresh:3599}",key = "'uuid_'+#uuid")
	@Cacheable(value="users#${select.cache.timeout:3600}",key = "'uuid_'+#uuid")
	public User get(Integer uuid) {
		// TODO Auto-generated method stub
		return userMapper.get(uuid);
	}
	
	@CacheEvict(value="users",key = "'uuid_'+#uuid")  
	public void delete(Integer uuid) {
		// TODO Auto-generated method stub
		userMapper.delete(uuid);
	}

}
