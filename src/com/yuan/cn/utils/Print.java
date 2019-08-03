package com.yuan.cn.utils;

public class Print {
    public static void main(String[] args) {
        print(10, 10);
        print1(10);
    }
    private static void print1(int n){
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
}
