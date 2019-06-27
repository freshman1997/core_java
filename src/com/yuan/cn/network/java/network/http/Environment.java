package com.yuan.cn.network.java.network.http;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Environment {


    private List<Class<?>> getAnnotation() throws IOException, ClassNotFoundException {
        String name =  this.getClass().getPackage().getName();

        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == '.')
                chars[i] = '/';
        }

        List<Class<?>> classList = new ArrayList<>();
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(new String(chars));
        while (resources.hasMoreElements()){
            String fileName = resources.nextElement().getFile();

            File file = new File(fileName);
            if(file.exists()) {
                File[] files = file.listFiles(fileFilter -> (fileFilter.getName().endsWith(".class")));
                if(files != null && files.length > 0) {
                    for (File file1 : files) {
                        Class<?> clazz = Class.forName(name + '.' + file1.getName().substring(0, file1.getName().indexOf(".")));
                        if (!(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())))
                            classList.add(clazz);
                    }
                }
            }
        }

        return classList.size() > 0 ? classList : null;
    }

    public Map<String, Object> loadComponent(){
        Map<String, Object> map = new HashMap<>();
        try {
            List<Class<?>> classList = getAnnotation();

            classList.forEach(item -> {
                load(item, map);
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map.size() > 0 ? map : null;
    }

    private void load(Class<?> item, Map<String, Object> map){

        if(item.isAnnotationPresent(Component.class)){
            Annotation[] annotations = item.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(Component.class)){

                    try {
                        Object o = item.newInstance();
                        Component component = (Component) annotation;
                        if(component.value().equals("")){
                            if(! map.containsKey(item.getSimpleName().substring(0, 1) + item.getSimpleName().substring(1)))
                            {
                                map.put(item.getSimpleName().substring(0, 1) + item.getSimpleName().substring(1), o);
                            }

                        }else
                        {
                            if(! map.containsKey(component.value()))
                            {
                                map.put(component.value(), o);
                            }
                        }

                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        if(item.isAnnotationPresent(Controller.class)){
            Annotation[] annotations = item.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(Controller.class)){
                    try {
                        Object o = item.newInstance();

                        if(! map.containsKey(item.getSimpleName().substring(0, 1).toLowerCase() + item.getSimpleName().substring(1))){
                            map.put(item.getSimpleName().substring(0, 1).toLowerCase() + item.getSimpleName().substring(1), o);
                        }

                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        if(item.isAnnotationPresent(Configuration.class)){

            try {
                Object o = item.newInstance();
                Annotation[] annotations = item.getAnnotations();
                for (Annotation annotation : annotations) {
                    if(annotation.annotationType().equals(Configuration.class)){
                        Configuration configuration = (Configuration) annotation;
                        map.put(item.getSimpleName().substring(0, 1) + item.getSimpleName().substring(1), o);

                        Method[] declaredMethods = item.getDeclaredMethods();
                        for (Method declaredMethod : declaredMethods) {
                            if(declaredMethod.isAnnotationPresent(Bean.class)){
                                Annotation[] annotations1 = declaredMethod.getAnnotations();
                                for (Annotation annotation1 : annotations1) {
                                    if(annotation.annotationType().equals(Bean.class)){
                                        Bean bean  = (Bean) annotation1;
                                        if(bean.value().equals("")){
                                            if(!map.containsKey(declaredMethod.getName())){
                                                Object invoke = declaredMethod.invoke(o);
                                                map.put(declaredMethod.getName(), invoke);
                                            }else {
                                                Object invoke = declaredMethod.invoke(o);
                                                map.put(bean.value(), invoke);
                                            }
                                        }
                                    }
                                }

                            }
                        }

                        Class<?>[] classes = configuration.baseClasses();
                        String[] s = configuration.basePackage();
                        if(classes.length > 0){
                            for (Class<?> aClass : classes) {
                                load(aClass, map);
                            }
                        }
                        if(s.length > 0){
                            for (String s1 : s) {
                                Class<?> aClass = Class.forName(s1);
                                load(aClass, map);
                            }
                        }
                    }
                }

            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void putAutowaried(Map<String, Object> map){
        if(map != null) {
            map.forEach((k, v) -> {

                Class<?> aClass = v.getClass();
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    if (declaredField.isAnnotationPresent(Autowaired.class)) {
                        // 属性的类型
                        Class<?> type = declaredField.getType();
                        map.forEach((k1, v1) -> {
                            if (v1.getClass().getSimpleName().equals(type.getSimpleName())) {
                                try {
                                    declaredField.set(v, v1);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

            });
        }
    }

    public String loadRequestMapping(Object o, Request request){
        Class<?> item = o.getClass();
        Method[] declaredMethods = item.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if(declaredMethod.isAnnotationPresent(RequestMapping.class)){
                Annotation[] annotations1 = declaredMethod.getAnnotations();
                for (Annotation annotation1 : annotations1) {
                    if(annotation1.annotationType().equals(RequestMapping.class)){
                        RequestMapping requestMapping = (RequestMapping) annotation1;
                        String production = requestMapping.production();
                        MethodType type = requestMapping.type();
                        String uri = requestMapping.uri();
                        String value = requestMapping.value();


                        if(! uri.equals("")){
                            return getString(o, request, declaredMethod, production, type, uri);
                        }else if(! value.equals("")) {
                            return  getString(o, request, declaredMethod, production, type, uri);
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getString(Object o, Request request, Method declaredMethod, String production, MethodType type, String uri) {
        if(request.getUri().equals(uri)){
            if(request.getMethod().equals(type.getMethod())){
                System.out.println(request.getMethod().equals(type.getMethod()));
                Parameter[] parameters = declaredMethod.getParameters();
                for (Parameter parameter : parameters) {
                    if(parameter.getType().getSimpleName().equals(request.getClass().getSimpleName())){
                        try {
                            Object invoke = declaredMethod.invoke(o, request);
                            String res = (String) invoke;
                            String s = new String(res.getBytes(), StandardCharsets.UTF_8);
                            if(production.equals(""))
                                return  HttpUtils.buildResponse(request, s, "text/html;charset=utf-8", 200, "ok");
                            else
                                return  HttpUtils.buildResponse(request, s, production, 200, "ok");

                        } catch (IllegalAccessException | InvocationTargetException e) {

                            e.printStackTrace();
                            return  HttpUtils.buildResponse(request, Arrays.toString(e.getStackTrace()), "text/html;charset=utf-8", 200, "ok");
                        }
                    }
                }
            }
        }
        return null;
    }


    public static void main(String[] args) {
        List<Class<?>> list = getClasses("com.yuan.cn.network.java.network.http");
        //list.forEach(System.out::println);
    }


    /**
     * 从包package中获取所有的Class
     * @param packageName 文件夹名称
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();

                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {

                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);

                } else if ("jar".equals(protocol)){
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();

                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();

                            System.out.println(name);

                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));

        assert dirfiles != null;

        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                                                        file.getAbsolutePath(),
                                                        recursive,
                                                        classes);

            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
