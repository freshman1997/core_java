package com.yuan.cn.network.bio_room.client;

import com.yuan.cn.network.bio_room.client.bean.ServerInfo;

public class Client {
    public static void main(String[] args) {
        ServerInfo info = UDPSearcher.getServerInfo(10000);
        System.out.println("Server:" + info);

        if (info != null) {
            TCPClient.startLink(info);
        }
    }
}
