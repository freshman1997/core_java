package com.yuan.cn.hotswap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaClassExecutor {
    public String execute(byte[] classBytes)
    {
        HackSystem.clearBuffer();
        ClassModifier modifier = new ClassModifier(classBytes);

        byte[] modifyBytes = modifier.modifyUTF8Constant("java/lang/System", "com/yuan/cn/hotswap/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modifyBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String.class});
            method.invoke(null, new String[]{null});
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }
}
