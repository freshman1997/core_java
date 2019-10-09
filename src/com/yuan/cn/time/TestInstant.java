package com.yuan.cn.time;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    @Test
    public void test1(){
        Instant instant = Instant.now();
        System.out.println(instant);
    }

    @Test
    public void test2(){
        LocalDate localDate = LocalDate.of(2008, 6, 26);
        System.out.println(localDate.getMonth());
        ZonedDateTime time = ZonedDateTime.of(LocalDate.of(2016, 2, 14),
                LocalTime.of(2, 30), ZoneId.of("Europe/Berlin"));
        System.out.println(time);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println(dateTimeFormatter.format(time));
    }

    @Test
    public void test3(){
        int n = 10;
        //parse(n - 1, n);
        parse(n - 1, n);

    }
    @Test
    public void test4(){
        int n = Integer.MAX_VALUE;
        System.out.println(isPalindrome(n));
        //System.out.println(1000000000 * 10);
    }

    private boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        int c = 1;
        long i = 10L;
        while (x % i != x){
            i = i * 10;
            c++;
        }

        int[] arr = new int[c];
        int k = 0;
        for (int j = (int) (i / 10); c > 0 && i != 0 && j != 0; j = j / 10) {
            arr[k] = (int) (x % i / j);
            k++;
            i = i / 10;
            c--;
        }
        k = 0;
        c = arr.length - 1;

        for (int j = 0; j < arr.length / 2; j++) {
            if (arr[j] == arr[c]){
                c --;
                k++;
            }
        }
        return k == arr.length / 2;
    }

    private void parse(int n, int s) {
        if (n < 1 || s >= Integer.MAX_VALUE)
            return;

        System.out.print(s + " = " + n);
        int c = n;

        while (c != s){
            System.out.print(" + 1");
            c++;
        }

        System.out.println();

        parse(n - 1 , s);

        c = n;
        int t = c;
        while (c != s){

            if (c + t == s && c != s - 1){
                System.out.println(s + " = " + n + " + " + c);
            }
            c++;
        }
    }

    private void parse1(int n){
        if (n <= 1 || n >= Integer.MAX_VALUE)
            throw new IllegalArgumentException("参数非法！");
        int c = n - 1;
        while (c > 0){
            int i = c;
            System.out.print(n + " = " + c + " + ");
            while (i < n){
                if (i < n -1)
                    System.out.print("1 + ");
                else
                    System.out.print("1");
                i++;
            }
            System.out.println();
            c--;
        }
        c = 2;
        while (c <= n / 2){
            System.out.print(n + " = " + c + " + ");
            int sum = 0;
            for (int i = c; i < n; i++) {
                sum += 1;
            }
            System.out.println(sum);
            c++;
        }

    }

    @Test
    public void test5(){
        System.out.println(romanToInt("LVIII"));
    }

    private int romanToInt(String s) {
        Roman[] values = Roman.values();
        int result = 0;
        for (int i = 0; i < s.length(); i++){
            for (int i1 = 0; i1 < values.length; i1++) {
                if (s.charAt(i) == values[i1].getSymbol().charAt(0)){
                    if (s.charAt(i) == 'X' || s.charAt(i) == 'C' || s.charAt(i) == 'M' ||
                            s.charAt(i) == 'V' || s.charAt(i) == 'D' || s.charAt(i) == 'L') {
                        if (i > 0 && ((s.charAt(i - 1) == 'I' && (s.charAt(i) == 'V' || s.charAt(i) == 'X'))
                                || (s.charAt(i - 1) == 'X' && (s.charAt(i) == 'L' || s.charAt(i) == 'C'))
                                || (s.charAt(i - 1) == 'C' && (s.charAt(i) == 'D' || s.charAt(i) == 'M')))) {
                            result += values[i1].getValue() - Roman.valueOf(s.charAt(i - 1) + "").getValue() * 2;
                        } else
                            result += values[i1].getValue();
                    }else
                        result += values[i1].getValue();
                }

            }
        }
        return result;
    }
}
enum Roman{
    I("I", 1),
    V("V", 5),
    X("X", 10),
    L("L", 50),
    C("C", 100),
    D("D", 500),
    M("M", 1000);
    private int value;
    private String symbol;
    private Roman(String symbol, int value){
        this.symbol = symbol;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }
}
