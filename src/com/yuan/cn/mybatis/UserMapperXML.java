package com.yuan.cn.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class UserMapperXML {
    public static final String namespace = "com.yuan.cn.mybatis.mapper.UserMapper";
    private static Map<String, String> methodMap = new HashMap<>();
    static {
        methodMap.put("findUserById","select * from stu where sid = %s");
        methodMap.put("insertUser","insert into stu(sid,sname,saddress,slikes) values(%s,%s,%s,%s)");
    }
    public static String getMethodSQL(String method){
        return methodMap.get(method);
    }
}
