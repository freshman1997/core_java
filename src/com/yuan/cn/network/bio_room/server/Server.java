package com.yuan.cn.network.bio_room.server;

import com.yuan.cn.network.bio_room.constant.param.TCPConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer(TCPConstants.PORT_SERVER);
        boolean isSucceed = tcpServer.start();
        if (!isSucceed) {
            System.out.println("开启 TCP 服务器失败!");
            return;
        }

        UDPProvider.start(TCPConstants.PORT_SERVER);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        String str = "";
        do {
            try {
                str = bufferedReader.readLine();
                tcpServer.broadcast(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!"00bye00".equalsIgnoreCase(str));

        UDPProvider.stop();
        tcpServer.stop();
    }
}
