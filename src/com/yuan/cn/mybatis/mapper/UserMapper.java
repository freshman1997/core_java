package com.yuan.cn.mybatis.mapper;

import com.yuan.cn.mybatis.pojo.User;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public interface UserMapper {
    public User findUserById(int id);
    public void insertUser(User user);
}
