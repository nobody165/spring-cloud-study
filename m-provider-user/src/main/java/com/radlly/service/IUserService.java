package com.radlly.service;

import com.radlly.model.User;

public interface IUserService {
	
	int insert(User user);
	
	User get(Integer uuid);
	
	void delete(Integer uuid);

}
