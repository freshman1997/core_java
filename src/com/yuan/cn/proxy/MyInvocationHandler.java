package com.yuan.cn.proxy;

import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler{
    private Object target;

    public MyInvocationHandler(Object target)
    {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object p = method.invoke(target, args);
        after();
        return p;
    }
    public void before()
    {
        System.out.println("before is running...");
    }
    public void after()
    {
        System.out.println("after is calling...");
    }
}
