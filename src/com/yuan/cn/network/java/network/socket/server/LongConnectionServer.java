package com.yuan.cn.network.java.network.socket.server;

import com.yuan.cn.network.java.network.socket.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LongConnectionServer {

    private final int port;

    private static long timeout = 1000 * 8L;


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
                System.out.println("server start @ " + serverSocket.getInetAddress().getHostAddress() + ":" + port);
                Socket client;
                while (true)
                {
                    client = serverSocket.accept();
                    System.out.println("新客户端接入：ip = " + ((InetSocketAddress)client.getRemoteSocketAddress()).getAddress().getHostAddress() + " port = " + ((InetSocketAddress)client.getRemoteSocketAddress()).getPort());
                    System.out.println();

                    HandleReadThread readThread = new HandleReadThread(client);
                    readThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static class HandleReadThread extends Thread{

        private boolean isTimeout = false;
        private long lastActionTime = System.currentTimeMillis();
        private MessageReader reader = new ServerMessageReader();
        private Socket client;
        private MessageSender sender = new ServerMessageSender();
        private int counter = 0;
        private HandleReadThread(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            InputStream inputStream;
            while (!isTimeout){

                try {

                    if ((System.currentTimeMillis() - lastActionTime) > timeout){
                        isTimeout = true;
                        System.out.println("超时!\n");
                        client.close();
                        break;
                    }

                    inputStream = client.getInputStream();

                    if (client.getInputStream().available() > 0)
                    {
                        Object o = reader.readHeartBeat(inputStream);
                        if (o != null)
                        {
                            System.out.println(o);
                        }

                        System.out.println("Message receive : " + reader.readTextMessage(inputStream));
                        handleWrite();
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        private void handleWrite() {
            try {
                OutputStream outputStream = client.getOutputStream();
                sender.sendHeartBeat(new HeartBeat(counter++), outputStream);

                sender.sendMessage(("你好世界，我是服务端，一个沙雕是也！\nend\n").getBytes(), outputStream);
                TimeUnit.SECONDS.sleep(2);
                lastActionTime = System.currentTimeMillis();

                if (counter > 10)
                    TimeUnit.SECONDS.sleep(10);


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
