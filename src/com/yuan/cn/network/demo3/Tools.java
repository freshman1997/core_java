package com.yuan.cn.network.demo3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tools {

    private static List<String> fileList = new ArrayList<>();

    // 计算机存储数据机制：正数存储的二进制原码,负数存储的是二进制的补码。  补码是负数的绝对值反码加1。
    public static int byteArrayToInt(byte[] b) {
        // 这里&上0xFF是为了保证二进制补码的一致性
        // 高24为计算机会补为1 &上之后就可以变回原来的补码的值
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static void main(String[] args) {
//        getFiles("G:\\教程\\专为程序员设计的线性代数课程【完】");
//        fileList.forEach(item->{
//            System.out.println(item);
//            File file = new File(item);
//            String newName = item.substring(0, item.lastIndexOf("\\")) +"\\"+ item.substring(item.lastIndexOf("】")+1);
//            File newFile = new File(newName);
//            file.renameTo(newFile);
//        });
//        doRenameBatch("G:\\教程\\C_C++\\c++\\1-C++高级开发内存管理专题讲解视频教程附课件代码 60课");

        System.out.println("行数："+lineNumberCounter("D:\\Golang\\mygolang\\src\\demo1\\open-bilibili"));
        System.out.println(fileList.size());
    }

    // 文件重命名操作
    public static final void doRenameBatch(final String path) {

        File file = new File(path);
        if (file.isDirectory()) {
            String[] list = file.list();
            File realFile = null;
            for (String fileName : list) {
                String newName = fileName.substring(fileName.lastIndexOf("]")+ 1, fileName.length());
                realFile = new File(file.getPath() + "\\" + fileName);
                File newNameFile = new File(file.getPath() + "\\" + newName);
                System.out.println(fileName + "\t 重命名为：\t" + newName);
                realFile.renameTo(newNameFile);
            }
        }
    }

    /**
     * 获取文件行数的例子
     * @param path
     * @return
     */
    public static int lineNumberCounter(String path)
    {
        System.out.println("===============开始===============");
        getFiles(path);
        int sum = 0;
        LineNumberReader lineNumberReader = null;
        for (String file : fileList) {
            try {
                lineNumberReader = new LineNumberReader(new FileReader(new File(file)));
                lineNumberReader.skip(Long.MAX_VALUE);
                sum += lineNumberReader.getLineNumber();
                System.out.println("当前的文件为："+file+"\t\t\t\t 行数为："+lineNumberReader.getLineNumber());
                lineNumberReader = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        System.out.println("===============结束===============");
        return sum;
    }

    /**
     * 通过递归一个路径获取所有文件的路径，不限深度
     * @param path
     */
    static void getFiles(final String path)
    {
        File file = new File(path);
        String[] list = file.list();
        for (final String s : list) {
            File subFile = new File(file.getPath()+"\\"+s);
            if(subFile.isDirectory())
            {
                getFiles(subFile.getPath());
            }else if(subFile.isFile() && subFile.getName().endsWith(".go"))
            {
                fileList.add(subFile.getPath());
            }
        }
    }


}
