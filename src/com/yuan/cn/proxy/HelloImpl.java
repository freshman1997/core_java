package com.yuan.cn.proxy;

public class HelloImpl implements Hello{

    @Override
    public void say() throws Throwable
    {
        System.out.println(this.getClass().getName()+" is saying ");
    }

    @Override
    public void say1() throws Throwable {
        System.out.println("sssssssssssssssssssssssss");
    }

    @Override
    public void say2() throws Throwable {
        System.out.println("NOooooooooooooooo");
    }
}
