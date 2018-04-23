package com.radlly.service;

import java.util.List;

import com.radlly.model.User;

public interface IUserService {
	
	int insert(User user);
	
	User get(Integer uuid);
	
	User getByName(String name);
	
	void delete(Integer uuid);
	
	List<User> getAll(Long companyId);

}
