package com.yuan.cn.jython;

import org.python.util.PythonInterpreter;

import java.util.Properties;

public class PythonTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, args);
        PythonInterpreter interpreter = new PythonInterpreter();
        // 运行python语句
        interpreter.exec("a = '你好, Jython'");
        interpreter.exec("print a");
    }
}
