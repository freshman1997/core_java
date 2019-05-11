package com.yuan.cn.mybatis;

import com.yuan.cn.mybatis.mapper.UserMapper;
import com.yuan.cn.mybatis.pojo.User;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class Bootstrap {
    public static void main(String[] args) {
        MySqlSession sqlSession  = new MyDefaultSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        User user = new User();
//        user.setId(20);
//        user.setUsername("sss");
//        user.setPassword("root");
//        user.setEmail("458");
//        userMapper.insertUser(user);
        User user = userMapper.findUserById(1);
        System.out.println(user);
    }
}
