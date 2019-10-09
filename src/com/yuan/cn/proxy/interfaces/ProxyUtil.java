package com.yuan.cn.proxy.interfaces;

import java.lang.reflect.Proxy;

public final class ProxyUtil {
    private static Interpreter interpreter;
    public static void setInterpreter(Interpreter interpreter1){
        interpreter = interpreter1;
    }
    public static Interpreter getInterpreter() {
        if(interpreter == null)
            return new DefaultInterpreter();
        return interpreter;
    }

    public static <T> T getRepository(Class<T> type)
    {
        // 创建代理对象，在执行对应的方法时会调用 RepositoryInvoker 的invoke方法；
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new RepositoryInvoker());
    }
}
