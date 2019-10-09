package com.yuan.cn.network.java.network.socket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartBeat implements Serializable {
    private final int times;

    public HeartBeat(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 当前次数为：" + times;
    }
}
