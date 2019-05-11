package com.yuan.cn.proxy.interfaces;

public interface Interpreter {

    void beforeQuery(ProcessJoinPoint processJoinPoint);
    void afterQueryCompleted(ProcessJoinPoint processJoinPoint);
    void afterThrowingException(Exception e);

    void around(ProcessJoinPoint processJoinPoint) throws Exception;
}
