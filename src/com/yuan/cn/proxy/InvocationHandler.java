package com.yuan.cn.proxy;

import java.lang.reflect.Method;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public interface InvocationHandler {
    /**
     * 生成代理的方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
