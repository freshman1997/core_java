package com.yuan.cn.proxy;

import org.springframework.util.FileCopyUtils;

import java.io.File;

public class MyClassLoader extends ClassLoader{

    private File dir;
    private String proxyClassPackage;

    public String getProxyClassPackage()
    {
        return proxyClassPackage;
    }

    public File getDir()
    {
        return dir;
    }

    public MyClassLoader(String path, String proxyClassPackage)
    {
        this.dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(dir != null)
        {
            File classFile = new File(dir,name +".class");

            if(classFile.exists())
            {
                try {
                    // 生成class的二进制字节流 这里的 FileCopyUtils 是spring的core模块的内容，里面封装了许多高级操作
                    byte[] classBytes = FileCopyUtils.copyToByteArray(classFile);
                    return defineClass(proxyClassPackage + "." + name,classBytes, 0, classBytes.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 如果上述自定义的类没有加载到 走默认的加载方式 也就是产生了错误，然后走默认
        return super.findClass(name);
    }
}
