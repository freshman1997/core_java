package com.yuan.cn.network.demo4.client;


import com.yuan.cn.network.demo4.client.bean.ServerInfo;

import java.io.IOException;

public class Client1
{
    public static void main(String[] args) {


        ServerInfo info = UDPSearcher.searchServer(10000);
        System.out.println("Server:" + info);

        if (info != null) {
            try {
                TCPClient.linkWith(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
