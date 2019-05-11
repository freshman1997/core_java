package com.yuan.cn.hotswap;

/**
 * 为了多次载入执行类而加入的加载器<br/>
 * defineClass方法开放出来，只有外部显示调用的时候才会用到loadByte方法
 * 有虚拟机调用时，仍然按照原有方法的双亲委派规则使用loadClass方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader{

    public HotSwapClassLoader()
    {
        super(HotSwapClassLoader.class.getClassLoader());
    }
    public Class loadByte(byte[] classBytes)
    {
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
