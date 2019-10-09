package com.yuan.cn.network.java.network.socket;

import java.io.IOException;
import java.io.OutputStream;

public interface MessageSender {

    void sendHeartBeat(Object heartBeat, OutputStream writer) throws IOException;

    void sendMessage(byte[] msgBuf, OutputStream writer) throws IOException;
}
