package com.yuan.cn.mybatis;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public interface MySqlSession {
    /**
     * get one
     * @param var1
     * @param <T>
     * @return
     */
    <T> T selectOne(String var1);

    /**
     * create the mapper instance
     * @param var1
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> var1);
}
