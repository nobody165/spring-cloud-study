package com.radlly.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.radlly.entity.Bootgrid;
import com.radlly.model.User;

public interface UserMapper {
    int delete(Integer id);

    int insert(User user);


    User get(Integer uuid);
    
    User getByName(@Param("username") String username);

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
    
    List<User> queryList1(long companyId);
    int countAll(Bootgrid bootgrid);
}