package com.yuan.cn.network.java.network.socket.server;

import com.yuan.cn.network.java.network.socket.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LongConnectionServer {

    private final int port;
    private static boolean isTimeout = false;
    private static long timeout = 1000 * 60L;

    private static long lastActionTime = System.currentTimeMillis();
    public LongConnectionServer(int port) {
        this.port = port;
    }

    public void startServer() {
        HandleConnection connection = new HandleConnection(this.port);
        connection.start();
    }

    private static class HandleConnection extends Thread{
        private final int port;

        private HandleConnection(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("server start @ " + serverSocket.getInetAddress().getHostAddress());

                while (true)
                {
                    Socket client = null;

                    client = serverSocket.accept();
                    HandleReadThread readThread = new HandleReadThread(client.getInputStream(), client.getOutputStream());
                    readThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static class HandleReadThread extends Thread{
        private InputStream inputStream;
        private OutputStream outputStream;
        private MessageReader reader = new ServerMessageReader();

        private HandleReadThread(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            while (!isTimeout){

                try {
                    if (System.currentTimeMillis() - lastActionTime > timeout)
                        isTimeout = true;
                    if (inputStream.available() > 0)
                    {
                        Object o = reader.readHeartBeat(inputStream);
                        if (o != null)
                        {
                            System.out.println(o);
                        }

                        //System.out.println("Message receive : " + reader.readTextMessage(inputStream));
                        lastActionTime = System.currentTimeMillis();

                        HandleWriteThread writeThread = new HandleWriteThread(outputStream);
                        writeThread.start();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
    private static class HandleWriteThread extends Thread{
        private MessageSender sender = new ServerMessageSender();
        private int counter = 0;
        private OutputStream outputStream;

        private HandleWriteThread(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            while (!isTimeout){
                if (System.currentTimeMillis() - lastActionTime > timeout) {
                    isTimeout = true;
                    break;
                }
                try {
                    sender.sendHeartBeat(new HeartBeat(counter++), outputStream);

                    //sender.sendMessage("你好世界，我是原智强，一个沙雕是也！\nend\n".getBytes(), outputStream);
                    TimeUnit.SECONDS.sleep(2);
                    lastActionTime = System.currentTimeMillis();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
