package com.yuan.cn.mybatis;

import com.yuan.cn.mybatis.pojo.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class MyMapperProxy implements InvocationHandler {
    private MySqlSession session;
    public MyMapperProxy(MySqlSession sqlSession) {
        this.session = sqlSession;
    }
    public MyMapperProxy(){}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass = method.getDeclaringClass().getName();
        if(UserMapperXML.namespace.equals(mapperClass))
        {
            String methodName = method.getName();
            String originSql = UserMapperXML.getMethodSQL(methodName);
            // 这里直接把之前的sql语句的%s替换
            if(args[0] instanceof User)
            {
                User users = (User) args[0];
                String formattedSql = String.format(originSql, users.getId(),"'"+users.getUsername()+"'","'"+users.getPassword()+"'","'"+users.getEmail()+"'");
                return session.selectOne(formattedSql);
            }else
            {
                String formattedSql = String.format(originSql,args);
                return session.selectOne(formattedSql);
            }
        }
        return null;
    }
}
