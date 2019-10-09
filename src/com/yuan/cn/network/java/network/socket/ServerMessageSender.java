package com.yuan.cn.network.java.network.socket;

import com.yuan.cn.network.java.network.socket.client.LongConnectionClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ServerMessageSender implements MessageSender{


    @Override
    public void sendHeartBeat(Object heartBeat, OutputStream writer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(heartBeat);
        writer.write(byteArrayOutputStream.toByteArray());
        LongConnectionClient.size = byteArrayOutputStream.size();
        writer.flush();
    }

    @Override
    public void sendMessage(byte[] msgBuf, OutputStream writer) throws IOException {
        writer.write(msgBuf);
        writer.flush();
    }
}
