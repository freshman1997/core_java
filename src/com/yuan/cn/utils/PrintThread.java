package com.yuan.cn.utils;

import java.util.concurrent.TimeUnit;

public class PrintThread extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100; i++)
        {
            if (Frame.isStop)
                break;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("当前为：" + i);

        }
        System.out.println("输出线程返回。");
    }

}
