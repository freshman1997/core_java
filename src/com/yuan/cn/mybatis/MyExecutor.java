package com.yuan.cn.mybatis;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public interface MyExecutor {
    /**
     * 总查询方法
     * @param statement
     * @param <T>
     * @return
     */
    public <T> T query(String statement);
}
