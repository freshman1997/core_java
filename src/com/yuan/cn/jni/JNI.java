package com.yuan.cn.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.UnsupportedEncodingException;

interface HelloInter extends Library {
    int add(int a, int b, char ops);
    Pointer hello();
}
public class JNI {

    public static void main(String[] args) throws UnsupportedEncodingException {

        HelloInter INSTANCE =
                (HelloInter) Native.loadLibrary("D:\\software\\Microsoft Visual Studio2019\\src\\Dll1\\x64\\Debug\\Dll1",
                        HelloInter.class);
        String wideString = INSTANCE.hello().getWideString(0);
        String s = new String(wideString.getBytes(), "UTF-8");
        System.out.println(s);
    }
}
