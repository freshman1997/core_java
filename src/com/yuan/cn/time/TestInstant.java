package com.yuan.cn.time;

import com.sun.istack.internal.NotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class TestInstant {
    @NotNull
    private int id;
    public static void main(String[] args) throws ScriptException, FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println(Instant.now());
        LocalDate today = LocalDate.now();
        LocalDate of = LocalDate.of(1997, Month.JUNE, 26).plusDays(2);
        System.out.println(today);

        System.out.println(of);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine nashorn = manager.getEngineByName("nashorn");
        nashorn.eval("var a =100;print(a);");
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.forEach(System.out::println);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //compiler.run(null,new FileOutputStream(""),new FileOutputStream(""),"","","");
//        ClassLoader classLoader = ToolProvider.getSystemToolClassLoader();
//        Class<?> clazz = classLoader.loadClass("");
//        clazz.newInstance();
//        InvocationHandler handler = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        };
        new TestInstant().say(null);
    }
    public void say(String name)
    {

    }
}
