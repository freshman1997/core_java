package com.yuan.cn.proxy;

import java.io.IOException;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class Client {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        String path = "E:\\IDEAsoure\\core_java\\src\\com\\yuan\\cn\\proxy";
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        try {
            Hello helloProxy = (Hello) Proxy.newProxyInstance(new MyClassLoader(path,"com.yuan.cn.proxy"),Hello.class,handler);
            System.out.println(helloProxy.getClass().getName());
            helloProxy.say();
            helloProxy.say1();
            helloProxy.say2();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
