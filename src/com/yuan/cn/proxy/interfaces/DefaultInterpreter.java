package com.yuan.cn.proxy.interfaces;

public class DefaultInterpreter implements Interpreter{

    @Override
    public void beforeQuery(ProcessJoinPoint processJoinPoint) {
    }

    @Override
    public void afterQueryCompleted(ProcessJoinPoint processJoinPoint) {
    }

    @Override
    public void afterThrowingException(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void around(ProcessJoinPoint processJoinPoint) {
    }
}
