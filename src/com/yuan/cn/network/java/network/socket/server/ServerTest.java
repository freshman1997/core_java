package com.yuan.cn.network.java.network.socket.server;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        LongConnectionServer server = new LongConnectionServer(8888);
        server.startServer();
    }
}
