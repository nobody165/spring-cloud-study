package com.radlly.mapper;

import java.util.List;

import com.radlly.entity.Bootgrid;
import com.radlly.model.User;

public interface UserMapper {
    int delete(Integer id);

    int insert(User user);


    User get(Integer uuid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名获得用户
     * @param email
     * @return
     */
//    User findByUserEmail(String email);
    //查询用户列表
    List<User> queryList(Bootgrid bootgrid);
    int countAll(Bootgrid bootgrid);
}