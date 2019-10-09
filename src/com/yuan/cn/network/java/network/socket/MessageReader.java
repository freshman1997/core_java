package com.yuan.cn.network.java.network.socket;

import java.io.IOException;
import java.io.InputStream;

public interface MessageReader {
    Object readHeartBeat(InputStream inputStream) throws IOException, ClassNotFoundException;

    String readTextMessage(InputStream inputStream) throws IOException;

}
