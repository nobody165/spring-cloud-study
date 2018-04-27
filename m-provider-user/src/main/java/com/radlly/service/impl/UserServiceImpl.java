package com.radlly.service.impl;

import java.util.List;

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
//由于spring boot 2.0 使用letuce  暂时代码还没有update 缓存
//	@Cacheable(value="users#${select.cache.timeout:3600}#${select.cache.refresh:3599}",key = "'uuid_'+#uuid")
//	@Cacheable(value="users#${select.cache.timeout:3600}",key = "'uuid_'+#uuid")
	public User get(Integer uuid) {
		// TODO Auto-generated method stub
		return userMapper.get(uuid);
	}
	
//	@CacheEvict(value="users",key = "'uuid_'+#uuid")  
	public void delete(Integer uuid) {
		// TODO Auto-generated method stub
		userMapper.delete(uuid);
	}

	@Override
	public List<User> getAll(Long companyId) {
		// TODO Auto-generated method stub
		return userMapper.queryList1(companyId);
	}

	@Override
	public User getByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.getByName(username);
	}
	
	

}
