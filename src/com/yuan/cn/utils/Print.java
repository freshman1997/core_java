package com.yuan.cn.utils;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Print {
    public static void main(String[] args) throws IOException {


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(byteArrayOutputStream);
//        System.setOut(printStream);

//        print(10, 10);
//        print1(10);

//        System.out.println("Hello World!!!!");
//        System.out.println("你好世界！！！");
//
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream));
//
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/logs/test.txt")));
//        String s;
//        while ( (s = reader.readLine()) != null)
//        {
//            writer.write(s + "\n");
//        }
//        writer.close();
//        reader.close();

        // 创建指定大小的空文件
//        FileOutputStream fileInputStream = new FileOutputStream("D:/logs/shell.dat");
//        FileChannel channel = fileInputStream.getChannel();
//        try {
//            channel.write(ByteBuffer.allocate(1), 1024 * 1024 * 1024);
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        channel.close();
//        fileInputStream.close();
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("D:/logs/shell.dat"), "rw");
        randomAccessFile.setLength(1024 * 1024 * 1024);

        randomAccessFile.close();

    }
    public static void print1(int n){
        int i = 0;
        int c = 2*n;
        while (i <= n){
            c = getC(i, c, c -2);
            i++;
            if(i == n){
                c = 0;
                while (i > 0 ){
                    c = getC(i, c, c + 2);
                    i--;
                }
                return;
            }
        }
    }

    private static int getC(int i, int c, int i2) {
        p(c);
        for(int j = 0; j < i - 1; j++){
            System.out.print("* ");
        }
        c = i2;
        for(int j = 0; j < i; j++){
            System.out.print("* ");
        }
        System.out.println();
        return c;
    }

    public static void print(int length,int height){
        int l = length;
        int len = 0;
        for(int j = 0; j < height; j++){
            p(l);
            System.out.print("*");
            mid(len*2);
            System.out.print("*");
            p(length);
            len++;
            System.out.println();
            l--;
        }
        l = 0;
        for(int j = 0; j < height +1; j++){
            p(l);
            if( j == height)
                System.out.print("*");
            System.out.print("*");
            mid(len*2);
            if( j != height)
                System.out.print("*");
            p(length);
            len--;
            System.out.println();
            l++;
        }

    }
    static void p(int length){
        for(int j = 0; j <length ;j++){
            System.out.print(" ");
        }
    }
    static void mid(int len){
        for(int i =0 ;i<len;i++){
            System.out.print(" ");
        }
    }
    @Test
    public void test() throws FileNotFoundException {
        int i = 0;
        PrintStream printStream = new PrintStream(new FileOutputStream("D:\\Books\\目录结构.txt"));
        System.setOut(printStream);
        printDirectory("D:\\Books", i);

    }

    @Test
    public void testByte() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i % (arr.length));
        }
    }

    /**
     * 目录结构打印
     * @param path 路径
     * @param i    层级
     */
    private void printDirectory(String path, int i){
        boolean exits = Files.exists(Paths.get(path));
        boolean isDirectory = Files.isDirectory(Paths.get(path));

        if (exits && isDirectory) {
            File parent = new File(path);
            if (i == 0)
                System.out.println(".|--" + parent.getName());
            i++;
            File[] files = parent.listFiles();
            if (files != null) {
                for (int i1 = 0; i1 < files.length; i1++) {
                    System.out.print("|");
                    for (int i2 = 0; i2 < i - 1; i2++) {
                        System.out.print("\t");
                        if (i >= 2)
                            System.out.print("|");
                    }
                    System.out.println("---" + files[i1].getName());
                    if (files[i1].isDirectory()) {
                        printDirectory(files[i1].getAbsolutePath(), i);
                    }
                }
            }
        }
    }

    @Test
    public void test1(){
       int n = 121;
       int m, count = 0;
       for (int i = 0; i <= n; i++){
           m = i;
           while (m != 0)
           {
               if (m % 10 == 1)
                   count++;
               m = m / 10;
           }
       }
       System.out.println(count);
    }
    private void training2(){
        int a = 32, p1 = 2, p2 = 7;
        a = a >> p1;
        for (int i = 0; i < (p2 - p1); i++) {
            int t = a & 1;
            System.out.print(t);
            a = a >> 1;
        }
    }
    private void count(){
        char c = ',';
        int count = 0;
        for (int i = 0; i < 8; i++){
            int t = c & 1;
            if ( t == 1){
                count ++;
            }
            c = (char) (c >> 1);
        }
        System.out.println(count);
    }

    private void divideNum(){
         int i, fi, s, t, fo;
         for (i = 1000; i <= 9999; i++){
             fi = i / 1000;
             s = i % 1000 / 100;
             t = i % 100 / 10;
             fo = i % 10;

             if ((fi + t) == 10 && (s * fo) == 12){
                 for (int j = 10; j <= 99; j++) {
                     if ((j * j) <= i && (j * j) == i)
                     {
                         System.out.println("==========");
                         System.out.println(i);
                         System.out.println("==========");
                     }
                 }
             }
         }


    }
}
