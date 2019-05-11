package com.yuan.cn.permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class PermissionTest {
    public static void main(String[] args) throws IOException {
//        System.setProperty("java.security.policy","E:/permissionTest.java.policy");
//        System.setSecurityManager(new SecurityManager());
        File f = new File("E:/1.txt");
        if(!f.exists())
        {
            f.createNewFile();
        }
        f.setReadable(true);
        f.setWritable(true);

    }
    public static void test()
    {
        MyPermission p = new MyPermission("hello sex,I love you drugs,like C++","insert");
        SecurityManager manager = System.getSecurityManager();
        if(manager != null)
        {
            p.badWorkSet().forEach(System.out::println);
            manager.checkPermission(p);
            System.out.println(manager);
        }
    }
}
