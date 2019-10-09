package com.yuan.cn.network.java.network.socket.client;

import com.yuan.cn.network.java.network.socket.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LongConnectionClient {
    public static int size;
    private final int port;
    private final String host;
    private static final long TIMEOUT = 1000 * 8L;
    private static boolean isTimeout = false;
    private static long lastActionTime = System.currentTimeMillis();
    private static boolean stop = false;
    private static Socket socket;

    public LongConnectionClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void startClient() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        WriteThread writeThread = new WriteThread();
        writeThread.start();
    }

    private static class WriteThread extends Thread{

        private MessageSender sender = new ServerMessageSender();
        private MessageReader reader = new ServerMessageReader();
        private int counter = 0;

        @Override
        public void run() {
            OutputStream outputStream = null;
            while (!isTimeout){
                if ((System.currentTimeMillis() - lastActionTime) > TIMEOUT){
                    isTimeout = true;
                    System.out.println("超时!");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                try {
                    outputStream = socket.getOutputStream();
                    sender.sendHeartBeat(new HeartBeat(counter++), outputStream);
                    byte[] bytes = "\n你好服务器，我是客户端，也是一个沙雕！\nend\n\n".getBytes();
                    sender.sendMessage(bytes, outputStream);

                    TimeUnit.SECONDS.sleep(2);
                    //lastActionTime = System.currentTimeMillis();

                    handleRead();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }
        }

        private void handleRead() {
            InputStream inputStream;
            try {
                inputStream = socket.getInputStream();
                if (inputStream.available() > 0){

                    Object o = reader.readHeartBeat(inputStream);
                    if (o != null)
                    {
                        System.out.println(o);
                    }else {
                        System.out.println("收到的对象为空");
                    }
                    System.out.println("Message receive : " + reader.readTextMessage(inputStream));
                    lastActionTime = System.currentTimeMillis();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
