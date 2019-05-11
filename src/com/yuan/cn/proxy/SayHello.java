package com.yuan.cn.proxy;

public class SayHello {
    public String sayHello(String name){
        System.out.println("Hello "+name);
        return "Hello "+name;
    }
}
