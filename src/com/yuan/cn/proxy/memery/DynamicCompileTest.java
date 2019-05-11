package com.yuan.cn.proxy.memery;

import sun.misc.IOUtils;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DynamicCompileTest {
    public static void main(String[] args) throws Exception {
        String fullName = "com.yuan.cn.proxy.SayHello";
        File file = new File("/Users/yangyu/Downloads/myclass");
        InputStream in = new FileInputStream("src/com/yuan/cn/proxy/SayHello.java");
        byte[] bytes = IOUtils.readFully(in, -1, false);
        String src = new String(bytes);
        in.close();

        System.out.println(src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance =  de.javaCodeToObject(fullName, src);
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods){
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                if(parameterType.getName().equals("java.lang.String"))
                    method.invoke(instance, "tom");
            }
        }

    }
}


/**
 * 用于将java源码保存在content属性中
 */
class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    /**
     * 保存java code
     */
    private String content;


    /**
     * 调用父类构造器，并设置content
     *
     * @param className
     * @param content
     */
    public CharSequenceJavaFileObject(String className, String content) {
        super(URI.create("string:///" + className.replace('.', '/')
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    /**
     * 实现getCharContent，使得JavaCompiler可以从content获取java源码
     *
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public String getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}
/**
 * 类文件管理器
 * 用于JavaCompiler将编译好后的class，保存到jclassObject中
 */
class ClassFileManager extends ForwardingJavaFileManager {

    /**
     * 保存编译后Class文件的对象
     */
    private JavaClassObject jclassObject;

    /**
     * 调用父类构造器
     * @param standardManager
     */
    public ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    /**
     * 将JavaFileObject对象的引用交给JavaCompiler，让它将编译好后的Class文件装载进来
     * @param location
     * @param className
     * @param kind
     * @param sibling
     * @return
     * @throws IOException
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
            throws IOException {
        if (jclassObject == null)
            jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }

    public JavaClassObject getJavaClassObject() {
        return jclassObject;
    }
}
/**
 * 将输出流交给JavaCompiler，最后JavaCompiler将编译后的class文件写入输出流中
 */
class JavaClassObject extends SimpleJavaFileObject {

    /**
     * 定义一个输出流，用于装载JavaCompiler编译后的Class文件
     */
    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    /**
     * 调用父类构造器
     * @param name
     * @param kind
     */
    public JavaClassObject(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    /**
     * 获取输出流为byte[]数组
     * @return
     */
    public byte[] getBytes() {
        return bos.toByteArray();
    }

    /**
     * 重写openOutputStream，将我们的输出流交给JavaCompiler，让它将编译好的Class装载进来
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }

    /**
     * 重写finalize方法，在对象被回收时关闭输出流
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        bos.close();
    }
}
/**
 * 在Java SE6中最好的方法是使用StandardJavaFileManager类。
 * 这个类可以很好地控制输入、输出，并且可以通过DiagnosticListener得到诊断信息，
 * 而DiagnosticCollector类就是listener的实现。
 * 使用StandardJavaFileManager需要两步。
 * 首先建立一个DiagnosticCollector实例以及通过JavaCompiler的getStandardFileManager()方法得到一个StandardFileManager对象。
 * 最后通过CompilationTask中的call方法编译源程序。
 */
class DynamicEngine {
    //单例
    private static DynamicEngine ourInstance = new DynamicEngine();

    public static DynamicEngine getInstance() {
        return ourInstance;
    }
    private URLClassLoader parentClassLoader;
    private String classpath;
    private DynamicEngine() {
        //获取类加载器
        this.parentClassLoader = (URLClassLoader) this.getClass().getClassLoader();

        //创建classpath
        this.buildClassPath();
    }


    /**
     * @MethodName    : 创建classpath
     */
    private void buildClassPath() {
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }

    /**
     * @MethodName    : 编译java代码到Object
     * @Description    : TODO
     * @param fullClassName   类名
     * @param javaCode  类代码
     * @return Object
     * @throws Exception
     */
    public Object javaCodeToObject(String fullClassName, String javaCode) throws Exception {
        long start = System.currentTimeMillis(); //记录开始编译时间
        Object instance = null;
        //获取系统编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 建立DiagnosticCollector对象
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // 建立用于保存被编译文件名的对象
        // 每个文件被保存在一个从JavaFileObject继承的类中
        ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));

        List<JavaFileObject> jfiles = new ArrayList<>();
        jfiles.add(new CharSequenceJavaFileObject(fullClassName, javaCode));

        //使用编译选项可以改变默认编译行为。编译选项是一个元素为String类型的Iterable集合
        List<String> options = new ArrayList<>();
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-classpath");
        options.add(this.classpath);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
        // 编译源程序
        boolean success = task.call();

        if (success) {
            //如果编译成功，用类加载器加载该类
            JavaClassObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
            Class clazz = dynamicClassLoader.loadClass(fullClassName,jco);
            instance = clazz.newInstance();
        } else {
            //如果想得到具体的编译错误，可以对Diagnostics进行扫描
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                error += compilePrint(diagnostic);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("javaCodeToObject use:"+(end-start)+"ms");
        return instance;
    }

    /**
     *  输出编译错误信息
     * @param diagnostic  sdasd
     * @return asdasd
     */
    private String compilePrint(Diagnostic diagnostic) {
        System.out.println("Code:" + diagnostic.getCode());
        System.out.println("Kind:" + diagnostic.getKind());
        System.out.println("Position:" + diagnostic.getPosition());
        System.out.println("Start Position:" + diagnostic.getStartPosition());
        System.out.println("End Position:" + diagnostic.getEndPosition());
        System.out.println("Source:" + diagnostic.getSource());
        System.out.println("Message:" + diagnostic.getMessage(null));
        System.out.println("LineNumber:" + diagnostic.getLineNumber());
        System.out.println("ColumnNumber:" + diagnostic.getColumnNumber());

        return "Code:[" + diagnostic.getCode() + "]\n" +
                "Kind:[" + diagnostic.getKind() + "]\n" +
                "Position:[" + diagnostic.getPosition() + "]\n" +
                "Start Position:[" + diagnostic.getStartPosition() + "]\n" +
                "End Position:[" + diagnostic.getEndPosition() + "]\n" +
                "Source:[" + diagnostic.getSource() + "]\n" +
                "Message:[" + diagnostic.getMessage(null) + "]\n" +
                "LineNumber:[" + diagnostic.getLineNumber() + "]\n" +
                "ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n";
    }
}
/**
 * 自定义类加载器
 */
class DynamicClassLoader extends URLClassLoader {
    public DynamicClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public Class findClassByClassName(String className) throws ClassNotFoundException {
        return this.findClass(className);
    }

    public Class loadClass(String fullName, JavaClassObject jco) {
        byte[] classData = jco.getBytes();
        return this.defineClass(fullName, classData, 0, classData.length);
    }
}