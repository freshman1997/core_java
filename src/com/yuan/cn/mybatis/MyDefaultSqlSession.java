package com.yuan.cn.mybatis;

import java.lang.reflect.Proxy;

public class MyDefaultSqlSession implements MySqlSession{

    private MyExecutor myExecutor = new MyBaseExecutor();
    @Override
    public <T> T selectOne(String var1) {
        return myExecutor.query(var1);
    }

    @Override
    public <T> T getMapper(Class<T> interfaces) {
        return (T)Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces},new MyMapperProxy(this));
    }
}
