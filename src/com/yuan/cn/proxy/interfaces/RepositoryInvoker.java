package com.yuan.cn.proxy.interfaces;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Crazy
 * @date 2019/5/6
 */
public final class RepositoryInvoker implements InvocationHandler {

    private Interpreter interpreter = ProxyUtil.getInterpreter();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String sql = AnnotationUtil.forSql(method.getAnnotations());

        Class<?> returnType = method.getReturnType();
        ProcessJoinPoint point = new ProcessJoinPoint();
        point.setMethod(method);
        point.setArgs(args);
        point.setObject(proxy);
        point.setSql(sql);
        interpreter.beforeQuery(point);

        try {

            if(sql==null || sql.equals(""))
                throw new RuntimeException("sql未定义！");

            Object query = SQLQuery.query(returnType,method, sql, args);

            interpreter.around(point);

            interpreter.afterQueryCompleted(point);

            return query;
        }catch (Exception e){
            try {
                interpreter.afterThrowingException(e);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return null;
    }
}
