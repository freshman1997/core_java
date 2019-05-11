package com.yuan.cn.proxy.interfaces;

import com.yuan.cn.proxy.interfaces.pojo.Stu;

public interface StuRepository {
    @SQL("select * from stu where sid = #id")
    Stu findStuById(int id);
}
