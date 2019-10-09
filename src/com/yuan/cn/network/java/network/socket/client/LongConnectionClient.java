package com.yuan.cn.network.java.network.socket.client;

import com.yuan.cn.network.java.network.socket.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LongConnectionClient {
    public static int size;
    private final int port;
    private final String host;
    private static final long TIMEOUT = 1000 * 60L;
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
    private static class ReadThread extends Thread
    {
        private MessageReader reader = new ServerMessageReader();

        @Override
        public void run() {
            while (!isTimeout){
                if (System.currentTimeMillis() - lastActionTime > TIMEOUT)
                    isTimeout = true;
                try {
                    if (socket.getInputStream().available() > 0){
                        Object o = reader.readHeartBeat(socket.getInputStream());
                        if (o != null)
                        {
                            System.out.println(o);
                        }else {
                            System.out.println("收到的对象为空");
                        }
                        //System.out.println("Message receive : " + reader.readTextMessage(inputStream));
//                        byte[] buff = new byte[1024];
//                        while (inputStream.read(buff) != -1);
                        lastActionTime = System.currentTimeMillis();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
    private static class WriteThread extends Thread{

        private MessageSender sender = new ServerMessageSender();
        private int counter = 0;

        @Override
        public void run() {
            while (!isTimeout){
                if (System.currentTimeMillis() - lastActionTime > TIMEOUT)
                    isTimeout = true;
                try {

                    sender.sendHeartBeat(new HeartBeat(counter++), socket.getOutputStream());
                    //byte[] bytes = "\n你好服务器，我是客户端，一个沙雕是也！\nend\n\n".getBytes();
                    //sender.sendMessage(bytes, socket.getOutputStream());

                    TimeUnit.SECONDS.sleep(2);
                    lastActionTime = System.currentTimeMillis();
                    //skipBytesFromStream(socket.getInputStream(), size);

                    ReadThread readThread = new ReadThread();
                    readThread.start();

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
    }

}
