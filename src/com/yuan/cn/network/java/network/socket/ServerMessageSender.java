package com.yuan.cn.network.java.network.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ServerMessageSender implements MessageSender{

    @Override
    public void sendHeartBeat(Object heartBeat, OutputStream writer) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writer);
        objectOutputStream.writeObject(heartBeat);
        writer.flush();
    }

    @Override
    public void sendMessage(byte[] msgBuf, OutputStream writer) throws IOException {
        writer.write(msgBuf);
        writer.flush();
    }
}
