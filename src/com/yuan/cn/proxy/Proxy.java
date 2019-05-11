package com.yuan.cn.proxy;

import org.springframework.util.FileCopyUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class Proxy {

    private static final String rt = "\r";

    public static Object newProxyInstance(MyClassLoader classLoader, Class<?> interfaces, InvocationHandler handler)
            throws IllegalArgumentException, IOException {
        if(handler == null)
        {
            throw new NullPointerException();
        }
        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassString = new StringBuffer();
        proxyClassString.append("package ")
                .append(classLoader.getProxyClassPackage())
                .append(";")
                .append(rt)
                .append("import java.lang.reflect.Method;")
                .append(rt)
                .append("public class $Proxy0 implements ")
                .append(interfaces.getName())
                .append("{")
                .append(rt)
                .append("private InvocationHandler handler;")
                .append(rt)
                .append("public $Proxy0(InvocationHandler handler){")
                .append(rt)
                .append("this.handler = handler;")
                .append("}")
                .append(rt)
                .append(getMethodString(methods,interfaces))
                .append("}");

        String filename = classLoader.getDir() + File.separator + "$Proxy0.java";
        File myProxyFile = new File(filename);
        System.out.println(filename);
        compile(proxyClassString, myProxyFile);
        try {
            Class $myProxy0 = classLoader.findClass("$Proxy0");

            Constructor constructor = $myProxy0.getConstructor(InvocationHandler.class);

            return constructor.newInstance(handler);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Method method : methods)
        {
            stringBuffer.
                    append("@Override").append(rt).
                    append("public void ")
                    .append(method.getName())
                    .append("()")
                    .append("throws Throwable{")
                    .append(rt)
                    .append("Method method1 = ")
                    .append(interfaces.getName())
                    .append(".class.getMethod(\"")
                    .append(method.getName())
                    .append("\",new Class[]{});")
                    .append(rt)
                    .append("this.handler.invoke(this,method1,null);")
                    .append(rt)
                    .append("}")
                    .append(rt);
        }
        return stringBuffer.toString();
    }

    private static void compile(StringBuffer proxyClassString, File myProxyFile) throws IOException {
        FileCopyUtils.copy(proxyClassString.toString().getBytes(), myProxyFile);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager.getJavaFileObjects(myProxyFile);

        JavaCompiler.CompilationTask javaCompilerTask = javaCompiler.getTask(null, standardFileManager, null, null, null, javaFileObjects);
        javaCompilerTask.call();
        standardFileManager.close();
    }
}
