package com.yuan.cn.jni;

public class JNI {
    static
    {
        System.loadLibrary("JNI");
    }
    public native String helloFromC();

    public static void main(String[] args) {
        System.out.println(new JNI().helloFromC());
    }
}
