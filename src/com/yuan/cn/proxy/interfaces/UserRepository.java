package com.yuan.cn.proxy.interfaces;

import com.yuan.cn.proxy.interfaces.pojo.User;

import java.util.List;

public interface UserRepository {

    @SQL("select * from user where id = #id")
    User selectById(@Param("id") int id);
    @SQL("select * from users where username = #username")
    User findByUsername(@Param("username") String username);
    @SQL("select * from user where id = #id and username = #username")
    User findUserByIdAndUsername(@Param("id") int id, @Param("username") String username);

    @SQL("insert users values(?,?,?)")
    int insert(User user);
    @SQL("update users set id=#id, username = #username, password = #password where id = #id")
    int update(User user);
    @SQL("delete from users where id = #id")
    int deleteById(@Param("id") int id);
    @SQL("select * from users u, stu s where u.id > 0 and s.sid > 0")
    List<User> findAll();
}
