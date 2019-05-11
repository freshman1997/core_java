package com.yuan.cn.network.bio_room.client;

import com.yuan.cn.network.bio_room.client.bean.ServerInfo;
import com.yuan.cn.network.bio_room.lib.utils.CloseUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {


    public static void startLink(ServerInfo serverInfo) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.setSoTimeout(3000);

            socket.connect(new InetSocketAddress(Inet4Address.getByName(serverInfo.getAddress()), serverInfo.getPort()), 3000);

            System.out.println("=====已经开始连接服务器端了=====");

            ReadHandler readHandler = new ReadHandler(socket.getInputStream());
            readHandler.start();

            writeData(socket);

            readHandler.exit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(Socket client) {
        PrintStream printer = null;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                // 构建键盘输入
                String msg = input.readLine();
                printer = new PrintStream(client.getOutputStream());
                printer.println(msg);
                if ("00bye00".equalsIgnoreCase(msg)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // 关闭资源
        printer.close();
    }

    private static class ReadHandler extends Thread {
        private boolean done = false;
        private final InputStream inputStream;

        ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            super.run();
            try {
                // 得到输入流，用于接收数据
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(inputStream));

                do {
                    String str;
                    try {
                        // 客户端拿到一条数据
                        str = socketInput.readLine();
                    } catch (SocketTimeoutException e) {
                        continue;
                    }
                    if (str == null) {
                        System.out.println("连接已关闭，无法读取数据！");
                        break;
                    }
                    // 打印到屏幕
                    System.out.println(str);
                } while (!done);
            } catch (Exception e) {
                if (!done) {
                    System.out.println("连接异常断开：" + e.getMessage());
                }
            } finally {
                // 连接关闭
                CloseUtils.close(inputStream);
            }
        }

        void exit() {
            done = true;
            CloseUtils.close(inputStream);
        }
    }
}
